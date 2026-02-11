package com.diva.database.user

import com.diva.models.user.User
import com.diva.models.user.permissions.UserPermission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserStorage {
    suspend fun count(): DivaResult<Long, DivaError>

    suspend fun getAll(limit: Int = 100, offset: Int = 0): DivaResult<List<User>, DivaError>

    fun getAllFlow(limit: Int = 100, offset: Int = 0): Flow<DivaResult<List<User>, DivaError>>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getById(id: Uuid): DivaResult<Option<User>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<User>, DivaError>>

    suspend fun insert(item: User): DivaResult<Unit, DivaError>

    suspend fun update(item: User): DivaResult<Unit, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun delete(id: Uuid): DivaResult<Unit, DivaError>

    suspend fun getByUsername(username: String): DivaResult<Option<User>, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(Option.Some("database: server action only"))
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateEmail(id: Uuid, email: String): DivaResult<Unit, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(Option.Some("database: server action only"))
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePassword(id: Uuid, passwordHash: String): DivaResult<Unit, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(Option.Some("database: server action only"))
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun insertPermissions(userId: Uuid, perm: UserPermission): DivaResult<Unit, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePermissions(userId: Uuid, perm: UserPermission): DivaResult<Unit, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deletePermissions(userId: Uuid, permId: Uuid): DivaResult<Unit, DivaError>
}
