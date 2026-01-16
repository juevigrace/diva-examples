package com.diva.models.api.social.chat.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageMediaResponse(
    @SerialName("media_id")
    val mediaId: String,
)
