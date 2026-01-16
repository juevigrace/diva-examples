package com.diva.models.api.collection.playlist.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlaylistSuggestionsDto(
    @SerialName("collection_id")
    val collectionId: String,
    @SerialName("media_id")
    val mediaId: String,
    @SerialName("status")
    val status: String
)
