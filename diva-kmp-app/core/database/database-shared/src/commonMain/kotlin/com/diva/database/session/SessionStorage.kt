package com.diva.database.session

import com.diva.models.auth.Session
import com.diva.models.session.SessionStatus
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.Storage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SessionStorage : Storage<Session> {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getSessionsByUser(userId: Uuid): DivaResult<List<Session>, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(
                    Option.Some("database: server action only")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteSessionsByUser(userId: Uuid): DivaResult<Unit, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(
                    Option.Some("database: server action only")
                )
            )
        )
    }

    suspend fun getCurrentSession(): DivaResult<Option<Session>, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(
                    Option.Some("database: client action only")
                )
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateStatus(sessionId: Uuid, status: SessionStatus): DivaResult<Unit, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(
                    Option.Some("database: server action only")
                )
            )
        )
    }
}
