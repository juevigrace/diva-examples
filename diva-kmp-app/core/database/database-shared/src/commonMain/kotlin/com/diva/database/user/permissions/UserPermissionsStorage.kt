package com.diva.database.user.permissions

import com.diva.models.user.permissions.UserPermission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserPermissionsStorage : Storage<UserPermission> {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(
                    Option.Some("database action not supported")
                ),
            )
        )
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<UserPermission>, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(
                    Option.Some("database action not supported")
                ),
            )
        )
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<UserPermission>, DivaError>> {
        return flowOf(
            DivaResult.failure(
                DivaError(
                    cause = ErrorCause.Error.NotImplemented(
                        Option.Some("database action not supported")
                    ),
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<UserPermission>, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(
                    Option.Some("database action not supported")
                ),
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<UserPermission>, DivaError>> {
        return flowOf(
            DivaResult.failure(
                DivaError(
                    cause = ErrorCause.Error.NotImplemented(
                        Option.Some("database action not supported")
                    ),
                )
            )
        )
    }
}
