package com.diva.session.database.shared

import com.diva.models.database.session.SessionEntity
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option

interface SessionStorage {
    suspend fun getSession(id: String): DivaResult<Option<SessionEntity>, DivaError>

    suspend fun getSessionsByUser(userId: String): DivaResult<List<SessionEntity>, DivaError>

    suspend fun saveSession(session: SessionEntity): DivaResult<Unit, DivaError>

    suspend fun updateSession(session: SessionEntity): DivaResult<Unit, DivaError>

    suspend fun deleteSession(id: String): DivaResult<Unit, DivaError>

    suspend fun deleteSessionsByUser(userId: String): DivaResult<Unit, DivaError>
}
