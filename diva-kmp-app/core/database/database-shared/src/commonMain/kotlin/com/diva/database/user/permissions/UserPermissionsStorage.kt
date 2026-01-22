package com.diva.database.user.permissions

import com.diva.models.user.permissions.UserPermission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.database.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserPermissionsStorage : Storage<UserPermission> {
    override suspend fun count(): DivaResult<Long, DivaError.DatabaseError> {
        return DivaResult.failure(
            DivaError.DatabaseError(
                operation = DatabaseAction.SELECT,
                table = "diva_user_permissions",
                details = "Operation not supported"
            )
        )
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<UserPermission>, DivaError.DatabaseError> {
        return DivaResult.failure(
            DivaError.DatabaseError(
                operation = DatabaseAction.SELECT,
                table = "diva_user_permissions",
                details = "Operation not supported"
            )
        )
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<UserPermission>, DivaError.DatabaseError>> {
        return flowOf(
            DivaResult.failure(
                DivaError.DatabaseError(
                    operation = DatabaseAction.SELECT,
                    table = "diva_user_permissions",
                    details = "Operation not supported"
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<UserPermission>, DivaError.DatabaseError> {
        return DivaResult.failure(
            DivaError.DatabaseError(
                operation = DatabaseAction.SELECT,
                table = "diva_user_permissions",
                details = "Operation not supported"
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<UserPermission>, DivaError.DatabaseError>> {
        return flowOf(
            DivaResult.failure(
                DivaError.DatabaseError(
                    operation = DatabaseAction.SELECT,
                    table = "diva_user_permissions",
                    details = "Operation not supported"
                )
            )
        )
    }
}
