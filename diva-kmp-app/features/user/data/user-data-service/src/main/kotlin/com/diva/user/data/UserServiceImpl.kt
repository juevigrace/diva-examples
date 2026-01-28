package com.diva.user.data

import com.diva.database.user.UserStorage
import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UserEmailDto
import com.diva.models.auth.Session
import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.asNetworkError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.flatMap
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.mapError
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import io.github.juevigrace.diva.core.tryResult
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserServiceImpl(
    private val storage: UserStorage,
) : UserService {
    override suspend fun getUsers(
        page: Int,
        pageSize: Int
    ): DivaResult<ApiResponse<PaginationResponse<User>>, DivaError.NetworkError> {
        return storage
            .getAll(pageSize, (page - 1) * pageSize)
            .mapError { err -> err.asNetworkError(HttpRequestMethod.GET, "/api/user") }
            .flatMap { users ->
                storage.count()
                    .mapError { err -> err.asNetworkError(HttpRequestMethod.GET, "/api/user") }
                    .map { count ->
                        ApiResponse(
                            data = PaginationResponse(
                                items = users,
                                totalItems = count.toInt(),
                                totalPages = ((count / pageSize) + 1).toInt(),
                                currentPage = page,
                                pageSize = pageSize,
                            ),
                            message = "Users retrieved"
                        )
                    }
            }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getUser(id: String): DivaResult<ApiResponse<User>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.GET, "/api/user/{id}")
            }
        ) {
            val parsedId: Uuid = Uuid.parse(id)
            storage
                .getById(parsedId)
                .mapError { err -> err.asNetworkError(HttpRequestMethod.GET, "/api/user/{id}") }
                .map { option ->
                    option.fold(
                        onNone = {
                            ApiResponse(
                                statusCode = HttpStatusCodes.NotFound.code,
                                message = "User not found",
                            )
                        },
                        onSome = { value -> ApiResponse(data = value, message = "User retrieved") }
                    )
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateUser(
        dto: UpdateUserDto,
        id: String,
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        val parsedId: Uuid = Uuid.parse(id)
        val user = User(
            id = parsedId,
            username = dto.username,
            alias = dto.alias,
            avatar = dto.avatar,
            bio = dto.bio,
        )
        return storage
            .update(user)
            .mapError { err -> err.asNetworkError(HttpRequestMethod.PUT, "/api/user") }
            .map { ApiResponse<Nothing>(message = "User updated") }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateEmail(
        dto: UserEmailDto,
        session: Session
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return storage
            .updateEmail(session.user.id, dto.email)
            .mapError { err ->
                err.asNetworkError(HttpRequestMethod.PUT, "/api/user/email")
            }
            .map { ApiResponse<Nothing>(message = "User updated") }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteUser(id: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return storage
            .delete(id)
            .mapError { err ->
                err.asNetworkError(HttpRequestMethod.DELETE, "/api/user")
            }
            .map { ApiResponse<Nothing>(message = "User deleted") }
    }

    override suspend fun getUserByUsername(username: String): DivaResult<User, DivaError> {
        return storage
            .getByUsername(username)
            .flatMap { option ->
                option.fold(
                    onNone = {
                        DivaResult.failure(
                            DivaError.DatabaseError(
                                operation = DatabaseAction.SELECT,
                                table = "diva_users",
                                details = "User not found"
                            )
                        )
                    },
                    onSome = { value -> DivaResult.success(value) }
                )
            }
    }

    override suspend fun getUserByEmail(email: String): DivaResult<User, DivaError> {
        return storage
            .getByEmail(email)
            .flatMap { option ->
                option.fold(
                    onNone = {
                        DivaResult.failure(
                            DivaError.DatabaseError(
                                operation = DatabaseAction.SELECT,
                                table = "diva_users",
                                details = "User not found"
                            )
                        )
                    },
                    onSome = { value -> DivaResult.success(value) }
                )
            }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun createUser(
        dto: CreateUserDto,
        onVerification: suspend (userId: Uuid) -> DivaResult<Unit, DivaError>
    ): DivaResult<Uuid, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            val id: Uuid = Uuid.random()
            val user = User(
                id = id,
                email = dto.email,
                username = dto.username,
                passwordHash = Option.of(dto.password),
                alias = dto.alias,
                avatar = dto.avatar,
                bio = dto.bio,
                userVerified = false,
            )
            storage
                .insert(user)
                .flatMap { onVerification(id).map { id } }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePassword(
        id: Uuid,
        passwordHash: String
    ): DivaResult<Unit, DivaError> {
        return storage.updatePassword(id, passwordHash)
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateVerified(id: Uuid): DivaResult<Unit, DivaError> {
        return storage.updateVerified(id)
    }
}
