package com.diva.session.database.shared

import com.diva.models.auth.Session
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.database.Storage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface SessionStorage : Storage<Session> {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun getSessionsByUser(userId: Uuid): DivaResult<List<Session>, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun deleteSessionsByUser(userId: Uuid): DivaResult<Unit, DivaError>
}
