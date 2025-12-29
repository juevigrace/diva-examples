package com.diva.session.database.shared

import com.diva.models.database.auth.SessionEntity
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.database.Storage

interface SessionStorage : Storage<SessionEntity> {
    suspend fun getSessionsByUser(userId: String): DivaResult<List<SessionEntity>, DivaError>

    suspend fun deleteSessionsByUser(userId: String): DivaResult<Unit, DivaError>
}
