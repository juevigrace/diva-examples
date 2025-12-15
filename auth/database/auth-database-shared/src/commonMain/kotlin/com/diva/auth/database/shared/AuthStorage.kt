package com.diva.auth.database.shared

import com.diva.auth.models.database.SessionEntity

interface AuthStorage {
    suspend fun saveSession(session: SessionEntity)

    suspend fun getSession(sessionId: String): SessionEntity?

    suspend fun getSessionsByUserId(userId: String): List<SessionEntity>

    suspend fun updateSession(session: SessionEntity)

    suspend fun deleteSession(sessionId: String)

    suspend fun deleteSessionsByUserId(userId: String)
}
