package com.diva.models.auth

import com.diva.models.session.SessionStatus
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Session(
    val id: Uuid,
    val userId: Uuid,
    val accessToken: String,
    val refreshToken: String,
    val device: String,
    val status: SessionStatus,
    val ipAddress: String,
    val userAgent: String,
    val expiresAt: Instant,
    val expired: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
)
