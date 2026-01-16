package com.diva.models.api.social.chat.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateMessageDto(
    @SerialName("id")
    val id: String,
    @SerialName("content")
    val content: String,
)
