package com.diva.models.auth

import com.diva.models.session.SessionStatus
import com.diva.models.user.User
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
data class Session(
    val id: Uuid,
    val user: User,
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
