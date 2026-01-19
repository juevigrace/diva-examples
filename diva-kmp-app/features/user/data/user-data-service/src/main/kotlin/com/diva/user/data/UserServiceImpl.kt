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
import io.github.juevigrace.diva.core.errors.DivaErrorException
import io.github.juevigrace.diva.core.errors.asNetworkError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.fold
import io.github.juevigrace.diva.core.getOrThrow
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.mapError
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import io.github.juevigrace.diva.core.onFailure
import io.github.juevigrace.diva.core.tryResult
import kotlin.time.Clock
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
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    method = HttpRequestMethod.GET,
                    url = "/api/user",
                )
            }
        ) {
            storage
                .getAll(pageSize, (page - 1) * pageSize)
                .mapError { err -> throw DivaErrorException(err) }
                .map { users ->
                    val count: Long = storage.count().getOrThrow(onThrow = { err -> throw DivaErrorException(err) })
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
                e.toDivaError().asNetworkError(
                    method = HttpRequestMethod.GET,
                    url = "/api/user/{id}",
                )
            }
        ) {
            val parsedId: Uuid = Uuid.parse(id)
            storage
                .getById(parsedId)
                .mapError { err -> throw DivaErrorException(err) }
                .map { option ->
                    option.fold(
                        onNone = {
                            throw DivaErrorException(
                                DivaError.NetworkError(
                                    method = HttpRequestMethod.GET,
                                    url = "/api/user/{id}",
                                    statusCode = HttpStatusCodes.NotFound,
                                    details = "User not found"
                                )
                            )
                        },
                        onSome = { value -> ApiResponse(data = value, message = "User retrieved") }
                    )
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun updateUser(
        dto: UpdateUserDto,
        session: Session
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    method = HttpRequestMethod.PUT,
                    url = "/api/user",
                )
            }
        ) {
            val user = User(
                id = session.user.id,
                username = dto.username,
                alias = dto.alias,
                avatar = dto.avatar,
                bio = dto.bio,
                email = session.user.email,
                userVerified = session.user.userVerified,
                createdAt = session.user.createdAt,
                updatedAt = session.user.updatedAt,
            )
            storage
                .update(user)
                .mapError { err -> throw DivaErrorException(err) }
                .map { _ -> ApiResponse<Nothing>(message = "User updated") }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateEmail(
        dto: UserEmailDto,
        session: Session
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return storage
            .updateEmail(session.user.id, dto.email)
            .mapError { err ->
                err.asNetworkError(
                    method = HttpRequestMethod.PUT,
                    url = "/api/user/email",
                )
            }
            .map { _ -> ApiResponse<Nothing>(message = "User updated") }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteUser(id: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return storage
            .delete(id)
            .mapError { err ->
                err.asNetworkError(
                    method = HttpRequestMethod.DELETE,
                    url = "/api/user",
                )
            }
            .map { _ -> ApiResponse<Nothing>(message = "User deleted") }
    }

    override suspend fun getUserByUsername(username: String): DivaResult<User, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            storage
                .getByUsername(username)
                .onFailure { err -> throw DivaErrorException(err) }
                .map { option ->
                    option.fold(
                        onNone = {
                            throw DivaErrorException(
                                DivaError.DatabaseError(
                                    operation = DatabaseAction.SELECT,
                                    table = "diva_users",
                                    details = "User not found"
                                )
                            )
                        },
                        onSome = { value -> value }
                    )
                }
        }
    }

    override suspend fun getUserByEmail(email: String): DivaResult<User, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError() }
        ) {
            storage
                .getByEmail(email)
                .mapError { err -> throw DivaErrorException(err) }
                .map { option ->
                    option.fold(
                        onNone = {
                            throw DivaErrorException(
                                DivaError.DatabaseError(
                                    operation = DatabaseAction.SELECT,
                                    table = "diva_users",
                                    details = "User not found"
                                )
                            )
                        },
                        onSome = { value -> value }
                    )
                }
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
                .mapError { err -> throw DivaErrorException(err) }
                .map { _ ->
                    onVerification(id).onFailure { err ->
                        // TODO: this might not be correct
                        storage.delete(id).onFailure { err -> throw DivaErrorException(err) }
                        throw DivaErrorException(err)
                    }
                    id
                }
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
