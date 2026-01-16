package com.diva.models.api.social.interaction.comment.dtos

@Serializable
data class UpdateCommentDto(
    @SerialName("interaction_id")
    val interactionId: String,
    @SerialName("content")
    val content: String,
)
