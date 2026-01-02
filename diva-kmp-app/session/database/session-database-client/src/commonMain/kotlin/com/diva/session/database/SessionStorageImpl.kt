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
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

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
        return db.getOne { sessionQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Session>, DivaError>> {
        return db.getOneAsFlow { sessionQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getSessionsByUser(userId: Uuid): DivaResult<List<Session>, DivaError> {
        return db.getList { sessionQueries.findByUserId(userId.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun insert(item: Session): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                sessionQueries.insert(
                    id = item.id.toString(),
                    user_id = item.userId.toString(),
                    access_token = item.accessToken,
                    refresh_token = item.refreshToken,
                    device = item.device,
                    status = item.status.value,
                    ip_address = item.ipAddress,
                    user_agent = item.userAgent,
                    expires_at = item.expiresAt.toEpochMilliseconds(),
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun update(item: Session): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                sessionQueries.update(
                    id = item.id.toString(),
                    access_token = item.accessToken,
                    refresh_token = item.refreshToken,
                    device = item.device,
                    status = item.status.value,
                    ip_address = item.ipAddress,
                    user_agent = item.userAgent,
                    expires_at = item.expiresAt.toEpochMilliseconds(),
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                sessionQueries.delete(id.toString())
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun deleteSessionsByUser(userId: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                sessionQueries.deleteByUserId(userId.toString())
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
    private fun mapToEntity(
        id: String,
        userId: String,
        accessToken: String,
        refreshToken: String,
        device: String,
        status: String,
        ipAddress: String,
        userAgent: String,
        expiresAt: Long,
        createdAt: Long,
        updatedAt: Long
    ): Session {
        return Session(
            id = Uuid.parse(id),
            userId = Uuid.parse(userId),
            accessToken = accessToken,
            refreshToken = refreshToken,
            device = device,
            status = safeSessionStatus(status),
            ipAddress = ipAddress,
            userAgent = userAgent,
            expiresAt = Instant.fromEpochMilliseconds(expiresAt),
            expired = expiresAt < Clock.System.now().toEpochMilliseconds(),
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt),
        )
    }
}
