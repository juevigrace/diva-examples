package com.diva.session.database

import com.diva.database.DivaDB
import com.diva.models.auth.Session
import com.diva.models.session.safeSessionStatus
import com.diva.models.user.User
import com.diva.session.database.shared.SessionStorage
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.DivaErrorException
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant
import kotlin.time.toKotlinInstant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

class SessionStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : SessionStorage {
    override suspend fun count(): DivaResult<Long, DivaError.DatabaseError> {
        return db.use {
            val value: Long = sessionQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Session>, DivaError.DatabaseError> {
        return db.getList { sessionQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override suspend fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Session>, DivaError.DatabaseError>> {
        return db.getListAsFlow { sessionQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Session>, DivaError.DatabaseError> {
        return db.getOne { sessionQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Session>, DivaError.DatabaseError>> {
        return db.getOneAsFlow { sessionQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getSessionsByUser(userId: Uuid): DivaResult<List<Session>, DivaError.DatabaseError> {
        return db.getList { sessionQueries.findByUserId(userId.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Session): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                val expiresAt: OffsetDateTime = OffsetDateTime.ofInstant(
                    item.expiresAt.toJavaInstant(),
                    ZoneOffset.systemDefault()
                )
                sessionQueries.insert(
                    id = item.id.toJavaUuid(),
                    user_id = item.user.id.toJavaUuid(),
                    access_token = item.accessToken,
                    refresh_token = item.refreshToken,
                    device = item.device,
                    status = item.status.value,
                    ip_address = item.ipAddress,
                    user_agent = item.userAgent,
                    expires_at = expiresAt,
                )
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.INSERT,
                        "diva_session",
                        "Failed to insert"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Session): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                val expiresAt: OffsetDateTime = OffsetDateTime.ofInstant(
                    item.expiresAt.toJavaInstant(),
                    ZoneOffset.systemDefault()
                )
                sessionQueries.update(
                    access_token = item.accessToken,
                    refresh_token = item.refreshToken,
                    device = item.device,
                    status = item.status.value,
                    ip_address = item.ipAddress,
                    user_agent = item.userAgent,
                    expires_at = expiresAt,
                    id = item.id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.UPDATE,
                        "diva_session",
                        "Failed to update"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                sessionQueries.delete(id.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.DELETE,
                        "diva_session",
                        "Failed to delete"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteSessionsByUser(userId: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                sessionQueries.deleteByUserId(userId.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                throw DivaErrorException(
                    DivaError.DatabaseError(
                        DatabaseAction.DELETE,
                        "diva_session",
                        "Failed to delete"
                    )
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private fun mapToEntity(
        id: UUID,
        accessToken: String,
        refreshToken: String,
        device: String,
        status: String,
        ipAddress: String,
        userAgent: String,
        expiresAt: OffsetDateTime,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        userId: UUID?,
        email: String?,
        username: String?,
        uCreatedAt: OffsetDateTime?,
        uUpdatedAt: OffsetDateTime?,
    ): Session {
        require(userId != null) { "User ID cannot be null" }
        require(email != null) { "Email cannot be null" }
        require(username != null) { "Username cannot be null" }
        require(uCreatedAt != null) { "User created at cannot be null" }
        require(uUpdatedAt != null) { "User updated at cannot be null" }

        return Session(
            id = id.toKotlinUuid(),
            user = User(
                id = userId.toKotlinUuid(),
                username = username,
                email = email,
                createdAt = uCreatedAt.toInstant().toKotlinInstant(),
                updatedAt = uUpdatedAt.toInstant().toKotlinInstant()
            ),
            accessToken = accessToken,
            refreshToken = refreshToken,
            device = device,
            status = safeSessionStatus(status),
            ipAddress = ipAddress,
            userAgent = userAgent,
            expiresAt = expiresAt.toInstant().toKotlinInstant(),
            expired = expiresAt.toInstant().toKotlinInstant() < Clock.System.now(),
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
        )
    }
}
