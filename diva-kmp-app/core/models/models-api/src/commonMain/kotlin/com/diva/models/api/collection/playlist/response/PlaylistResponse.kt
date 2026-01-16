package com.diva.models.api.collection.playlist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistResponse(
    @SerialName("collection_id")
    val collectionId: String,
    @SerialName("is_collaborative")
    val isCollaborative: Boolean,
    @SerialName("allow_suggestions")
    val allowSuggestions: Boolean
)
