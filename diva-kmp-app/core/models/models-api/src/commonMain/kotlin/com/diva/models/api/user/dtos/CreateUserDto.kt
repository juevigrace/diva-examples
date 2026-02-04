package com.diva.models.api.user.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(
    @SerialName("email")
    val email: String,
    @SerialName("username")
    val username: String = email,
    @SerialName("password")
    val password: String,
    @SerialName("alias")
    val alias: String,
    @SerialName("avatar")
    val avatar: String = "",
    @SerialName("bio")
    val bio: String = "",
)
