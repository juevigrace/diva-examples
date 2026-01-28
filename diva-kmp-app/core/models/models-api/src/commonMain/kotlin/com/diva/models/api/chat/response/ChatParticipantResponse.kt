package com.diva.models.api.chat.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatParticipantResponse(
    @SerialName("user_id")
    val userId: String,
    @SerialName("role")
    val role: String,
    @SerialName("joined_at")
    val joinedAt: Long,
    @SerialName("last_read_at")
    val lastReadAt: Long? = null,
    @SerialName("muted_until")
    val mutedUntil: Long? = null,
    @SerialName("added_by")
    val addedBy: String,
)
