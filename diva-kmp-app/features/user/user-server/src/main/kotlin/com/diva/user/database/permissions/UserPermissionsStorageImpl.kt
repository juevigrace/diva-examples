package com.diva.user.database.permissions

import com.diva.database.DivaDB
import com.diva.database.user.permissions.UserPermissionsStorage
import com.diva.models.user.permissions.UserPermission
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.DivaDatabase
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class UserPermissionsStorageImpl(
    private val db: DivaDatabase<DivaDB>,
) : UserPermissionsStorage {
    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: UserPermission): DivaResult<Unit, DivaError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userPermissionsQueries.insert(
                    id = item.id.toJavaUuid(),
                    user_id = item.userId.toJavaUuid(),
                    permission_id = item.permissionId.toJavaUuid(),
                    granted_by = item.grantedBy.toJavaUuid(),
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
                    user_id = item.userId.toJavaUuid(),
                    permission_id = item.permissionId.toJavaUuid(),
                    granted_by = item.grantedBy.toJavaUuid(),
                    id = item.id.toJavaUuid()
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
                userPermissionsQueries.delete(id.toJavaUuid())
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
}

