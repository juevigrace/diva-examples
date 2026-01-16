package com.diva.models.api.collection.playlist.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreatePlaylistContributorDto(
    @SerialName("contributor_id")
    val contributorId: String,
    @SerialName("collection_id")
    val collectionId: String
)
