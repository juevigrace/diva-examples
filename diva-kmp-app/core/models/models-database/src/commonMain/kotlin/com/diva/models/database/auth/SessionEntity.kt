package com.diva.models.database.auth

import com.diva.models.session.SessionStatus
import io.github.juevigrace.diva.core.models.Option

data class SessionEntity(
    val id: String,
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val device: String,
    val status: SessionStatus,
    val ipAddress: String,
    val userAgent: String,
    val expiresAt: Option<String> = Option.None,
    val expired: Option<Boolean> = Option.None,
    val createdAt: Long,
    val updatedAt: Long,
)
