package com.diva.models.api.social.post.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostDto(
    @SerialName("post_id")
    val postId: String,
    @SerialName("title")
    val title: String? = null,
    @SerialName("content")
    val content: String? = null,
    @SerialName("visibility")
    val visibility: String? = null,
)
