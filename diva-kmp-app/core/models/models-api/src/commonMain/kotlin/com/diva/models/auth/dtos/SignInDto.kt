package com.diva.models.auth.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInDto(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("device")
    val device: String,
    @SerialName("ip_address")
    val ipAddress: String? = null,
)
