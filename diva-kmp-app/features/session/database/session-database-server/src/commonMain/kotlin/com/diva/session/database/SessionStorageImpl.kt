package com.diva.session.database

import com.diva.database.DivaDB
import com.diva.models.auth.Session
import com.diva.models.session.safeSessionStatus
import com.diva.session.database.shared.SessionStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
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
    override suspend fun getAll(limit: Int, offset: Int): DivaResult<List<Session>, DivaError> {
        return db.getList { sessionQueries.findAll(mapper = ::mapToEntity) }
    }

    override suspend fun getAllFlow(limit: Int, offset: Int): Flow<DivaResult<List<Session>, DivaError>> {
        return db.getListAsFlow { sessionQueries.findAll(mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Session>, DivaError> {
        return db.getOne { sessionQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Session>, DivaError>> {
        return db.getOneAsFlow { sessionQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getSessionsByUser(userId: Uuid): DivaResult<List<Session>, DivaError> {
        return db.getList { sessionQueries.findByUserId(userId.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun insert(item: Session): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                val expiresAt: OffsetDateTime = OffsetDateTime.ofInstant(
                    item.expiresAt.toJavaInstant(),
                    ZoneOffset.systemDefault()
                )
                sessionQueries.insert(
                    id = item.id.toJavaUuid(),
                    user_id = item.userId.toJavaUuid(),
                    access_token = item.accessToken,
                    refresh_token = item.refreshToken,
                    device = item.device,
                    status = item.status.value,
                    ip_address = item.ipAddress,
                    user_agent = item.userAgent,
                    expires_at = expiresAt,
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    override suspend fun update(item: Session): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
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
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                sessionQueries.delete(id.toJavaUuid())
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteSessionsByUser(userId: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                sessionQueries.deleteByUserId(userId.toJavaUuid())
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private fun mapToEntity(
        id: UUID,
        userId: UUID,
        accessToken: String,
        refreshToken: String,
        device: String,
        status: String,
        ipAddress: String,
        userAgent: String,
        expiresAt: OffsetDateTime,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime
    ): Session {
        return Session(
            id = id.toKotlinUuid(),
            userId = userId.toKotlinUuid(),
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
