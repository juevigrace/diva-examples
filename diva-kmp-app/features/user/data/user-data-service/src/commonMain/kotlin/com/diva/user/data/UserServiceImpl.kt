package com.diva.user.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.PaginationResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UserEmailDto
import com.diva.models.auth.Session
import com.diva.models.user.User
import com.diva.user.database.shared.UserStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.toDivaError
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
    ): DivaResult<ApiResponse<PaginationResponse<User>>, DivaError> {
        return tryResult(
            onError = { e ->
                val err: DivaError = e.toDivaError()
                err as? DivaError.NetworkError
                    ?: DivaError.NetworkError("GET", "/user", 500, err.message, err.cause)
            }
        ) {
            val count: DivaResult.Success<Long> = storage.count()
                as? DivaResult.Success<Long>
                ?: throw DivaErrorException(DivaError.DatabaseError("SELECT", "users", "Failed to get user count"))
            storage
                .getAll(pageSize, (page - 1) * pageSize)
                .mapError { err ->
                    DivaError.NetworkError("GET", "/user", 500, err.message, err.cause)
                }
                .map { users ->
                    ApiResponse(
                        data = PaginationResponse(
                            items = users,
                            totalItems = count.value.toInt(),
                            totalPages = ((count.value / pageSize) + 1).toInt(),
                            currentPage = page,
                            pageSize = pageSize,
                        ),
                        message = "Users retrieved"
                    )
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getUser(id: String): DivaResult<ApiResponse<User>, DivaError> {
        return tryResult(
            onError = { e ->
                val err: DivaError = e.toDivaError()
                err as? DivaError.NetworkError
                    ?: DivaError.NetworkError("GET", "/user/{id}", 500, err.message, err.cause)
            }
        ) {
            val parsedId: Uuid = Uuid.parse(id)
            storage
                .getById(parsedId)
                .mapError { err ->
                    throw DivaErrorException(err)
                }
                .map { option ->
                    option.fold(
                        onNone = {
                            val err = DivaError.NetworkError("GET", "/user/{id}", 404, "User not found")
                            throw DivaErrorException(err)
                        },
                        onSome = { value ->
                            ApiResponse(data = value, message = "User retrieved")
                        }
                    )
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun updateUser(dto: UpdateUserDto, session: Session): DivaResult<ApiResponse<Nothing>, DivaError> {
        return tryResult(
            onError = { e ->
                val err: DivaError = e.toDivaError()
                err as? DivaError.NetworkError
                    ?: DivaError.NetworkError("PUT", "/user", 500, err.message, err.cause)
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
    ): DivaResult<ApiResponse<Nothing>, DivaError> {
        return tryResult(
            onError = { e ->
                val err: DivaError = e.toDivaError()
                err as? DivaError.NetworkError
                    ?: DivaError.NetworkError("PUT", "/user", 500, err.message, err.cause)
            }
        ) {
            storage
                .updateEmail(session.user.id, dto.email)
                .mapError { err -> throw DivaErrorException(err) }
                .map { _ -> ApiResponse<Nothing>(message = "User updated") }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteUser(id: Uuid): DivaResult<ApiResponse<Nothing>, DivaError> {
        return storage
            .delete(id)
            .mapError { err -> DivaError.NetworkError("DELETE", "/user", 500, err.message, err.cause) }
            .map { _ -> ApiResponse<Nothing>(message = "User deleted") }
    }

    override suspend fun getUserByUsername(username: String): DivaResult<User, DivaError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("UserService.getUserByUsername")
            }
        ) {
            storage
                .getByUsername(username)
                .mapError { err -> throw DivaErrorException(err) }
                .map { option ->
                    option.fold(
                        onNone = {
                            throw DivaErrorException(DivaError.DatabaseError("SELECT", "users", "User not found"))
                        },
                        onSome = { value ->
                            value
                        }
                    )
                }
        }
    }

    override suspend fun getUserByEmail(email: String): DivaResult<User, DivaError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("UserService.getUserByEmail")
            }
        ) {
            storage
                .getByEmail(email)
                .mapError { err -> throw DivaErrorException(err) }
                .map { option ->
                    option.fold(
                        onNone = {
                            throw DivaErrorException(DivaError.DatabaseError("SELECT", "users", "User not found"))
                        },
                        onSome = { value ->
                            value
                        }
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
            onError = { e ->
                e.toDivaError("UserService.createUser")
            }
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
                createdAt = Clock.System.now(),
                updatedAt = Clock.System.now(),
            )
            storage
                .insert(user)
                .mapError { err -> throw DivaErrorException(err) }
                .map { _ ->
                    onVerification(id).mapError { err ->
                        // TODO: check if this is ok
                        storage.delete(id).mapError { err -> throw DivaErrorException(err) }
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
        return tryResult(
            onError = { e ->
                e.toDivaError("UserService.updatePassword")
            }
        ) {
            storage
                .updatePassword(id, passwordHash)
                .mapError { err -> throw DivaErrorException(err) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateVerified(id: Uuid): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e ->
                e.toDivaError("UserService.updateVerified")
            }
        ) {
            storage.updateVerified(id)
                .mapError { err -> throw DivaErrorException(err) }
        }
    }
}
