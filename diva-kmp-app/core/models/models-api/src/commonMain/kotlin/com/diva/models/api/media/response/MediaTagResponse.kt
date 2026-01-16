package com.diva.models.api.media.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MediaTagResponse(
    @SerialName("media_id")
    val mediaId: String,
    @SerialName("tag_id")
    val tagId: String
)
