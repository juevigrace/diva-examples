package com.diva.auth.models.database

data class SessionEntity(
    val id: String,
    val userId: String,
    val accessToken: String,
    val refreshToken: String,
    val createdAt: String,
    val updatedAt: String,
    val expiresAt: String? = null,
    val deviceInfo: String? = null,
    val userAgent: String? = null,
    val isActive: Boolean? = null,
)