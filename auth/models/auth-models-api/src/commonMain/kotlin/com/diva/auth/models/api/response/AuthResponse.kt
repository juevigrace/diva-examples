package com.diva.auth.models.api.response

import kotlinx.serialization.SerialName

data class AuthResponse(
    @SerialName("session_id")
    val sessionId: String,
    @SerialName("access_token")
    val accessToken: String,
    @SerialName("refresh_token")
    val refreshToken: String,
)
