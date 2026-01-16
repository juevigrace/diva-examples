package com.diva.models.api.social.chat.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMessageMediaDto(
    @SerialName("media_id")
    val mediaId: String,
)
