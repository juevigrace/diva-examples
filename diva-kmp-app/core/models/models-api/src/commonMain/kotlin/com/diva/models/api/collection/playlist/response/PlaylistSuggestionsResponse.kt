package com.diva.models.api.collection.playlist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistSuggestionsResponse(
    @SerialName("collection_id")
    val collectionId: String,
    @SerialName("suggester_id")
    val suggesterId: String,
    @SerialName("media_id")
    val mediaId: String,
    @SerialName("status")
    val status: String,
    @SerialName("suggested_at")
    val suggestedAt: String
)
