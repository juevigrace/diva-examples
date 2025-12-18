package com.diva.auth.database

import com.diva.auth.database.shared.AuthStorage
import com.diva.models.database.session.SessionEntity
import com.diva.database.DivaDB
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.getOrElse
import io.github.juevigrace.diva.core.models.isEmpty
import io.github.juevigrace.diva.core.models.toDivaError
import io.github.juevigrace.diva.core.models.tryResult
import io.github.juevigrace.diva.database.Storage
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class AuthStorageImpl(
    private val storage: Storage<DivaDB>
) : AuthStorage {
    override suspend fun getSession(id: String): DivaResult<Option<SessionEntity>, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.getSession") }
        ) {
            val parsedId = UUID.fromString(id)
            storage.withDb {
                getOne(
                    db.sessionQueries.findById(
                        id = parsedId,
                        mapper = { id, userId, accessToken, refreshToken, device, status, ipAddress, expiresAt, createdAt, updatedAt ->
                            SessionEntity(
                                id = id.toString(),
                                userId = userId.toString(),
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                                device = device,
                                status = status,
                                ipAddress = ipAddress,
                                expiresAt = Option.of(expiresAt),
                                createdAt = createdAt,
                                updatedAt = updatedAt
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
                        mapper = { id, userId, accessToken, refreshToken, device, status, ipAddress, expiresAt, createdAt, updatedAt ->
                            SessionEntity(
                                id = id.toString(),
                                userId = userId.toString(),
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                                device = device,
                                status = status,
                                ipAddress = ipAddress,
                                expiresAt = Option.of(expiresAt),
                                createdAt = createdAt,
                                updatedAt = updatedAt
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
