package com.diva.user.database

import com.diva.database.user.permissions.UserPermissionsStorage
import com.diva.models.user.permissions.UserPermission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserPermissionsStorageImpl : UserPermissionsStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<UserPermission>, DivaError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<UserPermission>, DivaError>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<UserPermission>, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<UserPermission>, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: UserPermission): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: UserPermission): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }
}
