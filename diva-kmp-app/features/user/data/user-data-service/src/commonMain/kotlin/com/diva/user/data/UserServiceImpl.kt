package com.diva.user.data

import com.diva.models.api.ApiResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.api.user.dtos.UpdateUserDto
import com.diva.models.api.user.dtos.UpdateUserEmailDto
import com.diva.models.auth.Session
import com.diva.models.user.User
import com.diva.user.database.shared.UserStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.fold
import io.github.juevigrace.diva.core.models.map
import io.github.juevigrace.diva.core.models.mapError
import io.github.juevigrace.diva.core.models.toDivaError
import io.github.juevigrace.diva.core.models.tryResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserServiceImpl(
    private val storage: UserStorage,
) : UserService {
    override suspend fun getUsers(
        limit: Int,
        offset: Int
    ): DivaResult<ApiResponse<List<User>>, DivaError> {
        return storage.getAll(limit, offset)
            .mapError { err ->
                DivaError.NetworkError("GET", "/user", 500, err.message, err.cause)
            }
            .map { users ->
                ApiResponse(data = users, message = "Users retrieved")
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
            storage.getById(parsedId).mapError { err ->
                throw DivaErrorException(err)
            }.map { option ->
                option.fold(
                    onNone = {
                        throw DivaErrorException(DivaError.NetworkError("GET", "/user/{id}", 404, "User not found"))
                    },
                    onSome = { value ->
                        ApiResponse(data = value, message = "User retrieved")
                    }
                )
            }
        }
    }

    override suspend fun updateUser(dto: UpdateUserDto, session: Session): DivaResult<ApiResponse<Nothing>, DivaError> {
        TODO()
    }

    override suspend fun updateEmail(
        dto: UpdateUserEmailDto
    ): DivaResult<ApiResponse<Nothing>, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteUser(id: Uuid): DivaResult<ApiResponse<Nothing>, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByUsername(username: String): DivaResult<User, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun getUserByEmail(email: String): DivaResult<User, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createUser(
        dto: CreateUserDto,
        onVerification: suspend (userId: Uuid) -> DivaResult<Unit, DivaError>
    ): DivaResult<Uuid, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePassword(
        id: Uuid,
        passwordHash: String
    ): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateVerified(id: Uuid): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }
}
