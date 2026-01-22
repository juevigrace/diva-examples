package com.diva.user.database.permissions

import com.diva.database.DivaDB
import com.diva.database.user.permissions.UserPermissionsStorage
import com.diva.models.user.permissions.UserPermission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.database.DivaDatabase
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserPermissionsStorageImpl(
    private val db: DivaDatabase<DivaDB>,
) : UserPermissionsStorage {
    override suspend fun insert(item: UserPermission): DivaResult<Unit, DivaError.DatabaseError> {
        TODO()
    }

    override suspend fun update(item: UserPermission): DivaResult<Unit, DivaError.DatabaseError> {
        TODO()
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        TODO()
    }
}

