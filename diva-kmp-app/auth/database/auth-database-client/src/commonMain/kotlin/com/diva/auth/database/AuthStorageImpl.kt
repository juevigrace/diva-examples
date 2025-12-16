package com.diva.auth.database

import com.diva.auth.database.shared.AuthStorage
import com.diva.auth.models.database.SessionEntity

class AuthStorageImpl : AuthStorage {
    override suspend fun saveSession(session: SessionEntity) {
        // TODO: Implement session saving logic
    }

    override suspend fun getSession(sessionId: String): SessionEntity? {
        // TODO: Implement session retrieval logic
        return null
    }

    override suspend fun getSessionsByUserId(userId: String): List<SessionEntity> {
        // TODO: Implement sessions by user retrieval logic
        return emptyList()
    }

    override suspend fun updateSession(session: SessionEntity) {
        // TODO: Implement session update logic
    }

    override suspend fun deleteSession(sessionId: String) {
        // TODO: Implement session deletion logic
    }

    override suspend fun deleteSessionsByUserId(userId: String) {
        // TODO: Implement sessions by user deletion logic
    }
}
