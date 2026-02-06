package com.diva.models.api.media.tag.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateMediaTagDto(
    @SerialName("tag_id")
    val tagId: String,
)