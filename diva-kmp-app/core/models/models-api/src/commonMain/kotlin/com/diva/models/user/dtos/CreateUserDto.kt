package com.diva.models.user.dtos

import com.diva.models.auth.dtos.SessionDataDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(
    @SerialName("email")
    val email: String,
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("alias")
    val alias: String = username,
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("session_data")
    val sessionData: SessionDataDto
)
