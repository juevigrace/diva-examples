package com.diva.models.api.auth.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PasswordResetRequestDto(
    @SerialName("email")
    val email: String,
)

@Serializable
data class PasswordResetConfirmDto(
    @SerialName("code")
    val code: String,
)

@Serializable
data class PasswordUpdateDto(
    @SerialName("new_password")
    val newPassword: String,
)
