package com.diva.models.auth.response

import kotlinx.serialization.SerialName

data class AuthResponse(
    @SerialName("session_id")
    val sessionId: String,
    @SerialName("user_id")
    val userId: String,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
)
