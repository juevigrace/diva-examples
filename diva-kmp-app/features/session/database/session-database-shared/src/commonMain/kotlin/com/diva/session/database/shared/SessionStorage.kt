package com.diva.session.database.shared

import com.diva.models.auth.Session
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.database.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SessionStorage : Storage<Session> {
    override suspend fun count(): DivaResult<Long, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Operation not supported"))
    }

    override suspend fun getAll(limit: Int, offset: Int): DivaResult<List<Session>, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Operation not supported"))
    }

    override suspend fun getAllFlow(limit: Int, offset: Int): Flow<DivaResult<List<Session>, DivaError.DatabaseError>> {
        return flowOf(
            DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Operation not supported"))
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun getSessionsByUser(userId: Uuid): DivaResult<List<Session>, DivaError.DatabaseError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteSessionsByUser(userId: Uuid): DivaResult<Unit, DivaError.DatabaseError>
}
