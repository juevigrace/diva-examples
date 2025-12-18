package com.diva.models.database.session

import io.github.juevigrace.diva.core.models.Option

data class SessionEntity(
    val id: String,
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val device: String,
    val status: Boolean,
    val ipAddress: String,
    val userAgent: Option<String> = Option.None,
    val expiresAt: Option<Long> = Option.None,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        fun clientEntity(
            id: String,
            userId: String,
            accessToken: String,
            refreshToken: String,
            device: String,
            status: Boolean,
            ipAddress: String,
            userAgent: Option<String> = Option.None,
            createdAt: Long,
            updatedAt: Long,
        ): SessionEntity {
            return SessionEntity(
                id = id,
                userId = userId,
                accessToken = accessToken,
                refreshToken = refreshToken,
                device = device,
                status = status,
                ipAddress = ipAddress,
                userAgent = userAgent,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
