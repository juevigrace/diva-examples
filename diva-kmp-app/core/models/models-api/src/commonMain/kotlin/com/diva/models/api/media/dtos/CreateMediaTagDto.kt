package com.diva.models.api.media.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateMediaTagDto(
    @SerialName("media_id")
    val mediaId: String,
    @SerialName("tag_id")
    val tagId: String,
)
