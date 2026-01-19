package com.diva.database.session

import com.diva.models.auth.Session
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.database.Storage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SessionStorage : Storage<Session> {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getSessionsByUser(userId: Uuid): DivaResult<List<Session>, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Server action only"))
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteSessionsByUser(userId: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Server action only"))
    }

    suspend fun getCurrentSession(): DivaResult<Option<Session>, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Client action only"))
    }
}