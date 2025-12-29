package com.diva.session.database

import com.diva.database.DivaDB
import com.diva.models.database.auth.SessionEntity
import com.diva.session.database.shared.SessionStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.getOrElse
import io.github.juevigrace.diva.core.models.isEmpty
import io.github.juevigrace.diva.core.models.toDivaError
import io.github.juevigrace.diva.core.models.tryResult
import io.github.juevigrace.diva.database.DivaDatabase
import io.github.juevigrace.diva.database.Storage
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class SessionStorageImpl(
    private val storage: DivaDatabase<DivaDB>
) : SessionStorage {
    override suspend fun getById(id: String): DivaResult<Option<SessionEntity>, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.getSession") }
        ) {
            val parsedId = UUID.fromString(id)
            storage.withDb {
                getOne(
                    db.sessionQueries.findById(
                        id = parsedId,
                        mapper = { id, user_id, access_token, refresh_token, device, status, ip_address, user_agent, expires_at, created_at, updated_at ->
                            SessionEntity(
                                id = id.toString(),
                                userId = user_id.toString(),
                                accessToken = access_token,
                                refreshToken = refresh_token,
                                device = device,
                                status = status,
                                ipAddress = ip_address,
                                userAgent = Option.of(user_agent),
                                expiresAt = Option.of(expires_at),
                                createdAt = created_at,
                                updatedAt = updated_at,
                            )
                        }
                    )
                )
            }
        }
    }

    override suspend fun getSessionsByUser(userId: String): DivaResult<List<SessionEntity>, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.getSessionsByUser") }
        ) {
            val parsedId = UUID.fromString(userId)
            storage.withDb {
                getList(
                    db.sessionQueries.findByUserId(
                        user_id = parsedId,
                        mapper = { id, user_id, access_token, refresh_token, device, status, ip_address, user_agent, expires_at, created_at, updated_at ->
                            SessionEntity(
                                id = id.toString(),
                                userId = user_id.toString(),
                                accessToken = access_token,
                                refreshToken = refresh_token,
                                device = device,
                                status = status,
                                ipAddress = ip_address,
                                userAgent = Option.of(user_agent),
                                expiresAt = Option.of(expires_at),
                                createdAt = created_at,
                                updatedAt = updated_at,
                            )
                        }
                    )
                )
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun saveSession(session: SessionEntity): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.saveSession") }
        ) {
            if (session.expiresAt.isEmpty()) {
                throw DivaErrorException(DivaError.validation())
            }
            storage.withDb {
                val result = db.transaction {
                    db.sessionQueries.insert(
                        id = UUID.fromString(session.id),
                        user_id = UUID.fromString(session.userId),
                        access_token = session.accessToken,
                        refresh_token = session.refreshToken,
                        device = session.device,
                        status = session.status,
                        ip_address = session.ipAddress,
                        expires_at = session.expiresAt.getOrElse {
                            Clock.System.now().toEpochMilliseconds()
                        },
                        created_at = session.createdAt,
                        updated_at = session.updatedAt
                    )
                }
                DivaResult.success(result)
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    override suspend fun updateSession(session: SessionEntity): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.updateSession") }
        ) {
            storage.withDb {
                val result = db.transaction {
                    db.sessionQueries.update(
                        access_token = session.accessToken,
                        refresh_token = session.refreshToken,
                        device = session.device,
                        status = session.status,
                        ip_address = session.ipAddress,
                        updated_at = session.updatedAt,
                        expires_at = session.expiresAt.getOrElse {
                            Clock.System.now().toEpochMilliseconds()
                        },
                        id = UUID.fromString(session.id)
                    )
                }
                DivaResult.success(result)
            }
        }
    }

    override suspend fun deleteSession(id: String): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.updateSession") }
        ) {
            storage.withDb {
                val result = db.transaction {
                    db.sessionQueries.deleteById(UUID.fromString(id))
                }
                DivaResult.success(result)
            }
        }
    }

    override suspend fun deleteSessionsByUser(userId: String): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.updateSession") }
        ) {
            storage.withDb {
                val result = db.transaction {
                    db.sessionQueries.deleteByUserId(UUID.fromString(userId))
                }
                DivaResult.success(result)
            }
        }
    }
}
