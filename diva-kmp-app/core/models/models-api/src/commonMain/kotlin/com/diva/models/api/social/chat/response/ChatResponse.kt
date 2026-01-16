package com.diva.models.api.social.chat.response

import com.diva.models.api.user.response.UserResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatResponse(
    @SerialName("id")
    val id: String,
    @SerialName("created_by")
    val createdBy: String,
    @SerialName("type")
    val type: String,
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String,
    @SerialName("avatar")
    val avatar: String? = null,
    @SerialName("last_message_at")
    val lastMessageAt: Long? = null,
    @SerialName("created_at")
    val createdAt: Long,
    @SerialName("updated_at")
    val updatedAt: Long,
    @SerialName("deleted_at")
    val deletedAt: Long? = null
)