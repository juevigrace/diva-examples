package com.diva.models.api.social.post.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePostDto(
    @SerialName("post_type")
    val postType: String,
    @SerialName("title")
    val title: String,
    @SerialName("content")
    val content: String,
    @SerialName("visibility")
    val visibility: String,
    @SerialName("media_id")
    val mediaId: String? = null,
    @SerialName("collection_id")
    val collectionId: String? = null
)
