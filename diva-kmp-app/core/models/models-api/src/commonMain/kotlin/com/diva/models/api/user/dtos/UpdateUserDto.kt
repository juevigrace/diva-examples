package com.diva.models.api.user.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateUserDto(
    @SerialName("username")
    val username: String,
    @SerialName("alias")
    val alias: String = username,
    @SerialName("bio")
    val bio: String = "",
    @SerialName("avatar")
    val avatar: String = ""
)
