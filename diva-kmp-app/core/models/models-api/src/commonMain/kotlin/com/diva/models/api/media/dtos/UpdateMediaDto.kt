package com.diva.models.api.media.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateMediaDto(
    @SerialName("id")
    val id: String,
    @SerialName("alt_text")
    val altText: String? = null,
    @SerialName("visibility")
    val visibility: String? = null,
    @SerialName("sensitive_content")
    val sensitiveContent: Boolean? = null,
    @SerialName("adult_content")
    val adultContent: Boolean? = null,
    @SerialName("published_at")
    val publishedAt: Long? = null,
)
