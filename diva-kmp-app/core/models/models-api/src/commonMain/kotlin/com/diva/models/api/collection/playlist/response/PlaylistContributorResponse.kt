package com.diva.models.api.collection.playlist.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaylistContributorResponse(
    @SerialName("collection_id")
    val collectionId: String,
    @SerialName("contributor_id")
    val contributorId: String
)
