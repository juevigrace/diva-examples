package com.diva.user.database

import com.diva.database.DivaDB
import com.diva.database.user.permissions.UserPermissionsStorage
import com.diva.models.user.permissions.UserPermission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserPermissionsStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : UserPermissionsStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        return db.use {
            val value: Long = userPermissionsQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<UserPermission>, DivaError> {
        return db.getList { userPermissionsQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<UserPermission>, DivaError>> {
        return db.getListAsFlow { userPermissionsQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<UserPermission>, DivaError> {
        return db.getOne { userPermissionsQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<UserPermission>, DivaError>> {
        return db.getOneAsFlow { userPermissionsQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: UserPermission): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userPermissionsQueries.insert(
                    id = item.id.toString(),
                    user_id = item.userId.toString(),
                    permission_id = item.permissionId.toString(),
                    granted_at = item.grantedAt.toEpochMilliseconds(),
                    granted_by = item.grantedBy.toString(),
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.INSERT,
                            table = Option.Some("diva_user_permissions"),
                            details = Option.Some("Failed to insert")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: UserPermission): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userPermissionsQueries.update(
                    user_id = item.userId.toString(),
                    permission_id = item.permissionId.toString(),
                    granted_at = item.grantedAt.toEpochMilliseconds(),
                    granted_by = item.grantedBy.toString(),
                    created_at = item.createdAt.toEpochMilliseconds(),
                    updated_at = item.updatedAt.toEpochMilliseconds(),
                    id = item.id.toString()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.UPDATE,
                            table = Option.Some("diva_user_permissions"),
                            details = Option.Some("Failed to update")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userPermissionsQueries.delete(id.toString())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError(
                        ErrorCause.Database.NoRowsAffected(
                            action = DatabaseAction.DELETE,
                            table = Option.Some("diva_user_permissions"),
                            details = Option.Some("Failed to delete")
                        )
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private fun mapToEntity(
        id: String,
        userId: String,
        permissionId: String,
        grantedAt: Long,
        grantedBy: String,
        createdAt: Long,
        updatedAt: Long
    ): UserPermission {
        return UserPermission(
            id = Uuid.parse(id),
            userId = Uuid.parse(userId),
            permissionId = Uuid.parse(permissionId),
            grantedAt = Instant.fromEpochMilliseconds(grantedAt),
            grantedBy = Uuid.parse(grantedBy),
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt)
        )
    }
}
