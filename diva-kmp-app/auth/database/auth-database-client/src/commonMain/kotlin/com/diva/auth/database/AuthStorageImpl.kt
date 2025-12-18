package com.diva.auth.database

import com.diva.auth.database.shared.AuthStorage
import com.diva.models.database.session.SessionEntity
import com.diva.database.DivaDB
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.toDivaError
import io.github.juevigrace.diva.core.models.tryResult
import io.github.juevigrace.diva.database.Storage

class AuthStorageImpl(
    private val storage: Storage<DivaDB>
) : AuthStorage {
    override suspend fun getSession(id: String): DivaResult<Option<SessionEntity>, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.getSession") }
        ) {
            storage.withDb {
                getOne(db.sessionQueries.findById(id, mapper = SessionEntity::clientEntity))
            }
        }
    }

    override suspend fun getSessionsByUser(userId: String): DivaResult<List<SessionEntity>, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.getSessionsByUser") }
        ) {
            storage.withDb {
                getList(db.sessionQueries.findByUserId(userId, mapper = SessionEntity::clientEntity))
            }
        }
    }

    override suspend fun saveSession(session: SessionEntity): DivaResult<Unit, DivaError> {
        return tryResult(
            onError = { e -> e.toDivaError(origin = "AuthStorage.saveSession") }
        ) {
            storage.withDb {
                val result = db.transaction {
                    db.sessionQueries.insert(
                        id = session.id,
                        user_id = session.userId,
                        access_token = session.accessToken,
                        refresh_token = session.refreshToken,
                        device = session.device,
                        status = session.status,
                        ip_address = session.ipAddress,
                        created_at = session.createdAt,
                        updated_at = session.updatedAt
                    )
                }
                DivaResult.success(result)
            }
        }
    }

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
                        id = session.id
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
                    db.sessionQueries.deleteById(id)
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
                    db.sessionQueries.deleteByUserId(userId)
                }
                DivaResult.success(result)
            }
        }
    }
}
