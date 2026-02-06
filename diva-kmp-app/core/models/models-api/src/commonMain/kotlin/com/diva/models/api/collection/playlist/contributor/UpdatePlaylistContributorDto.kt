package com.diva.models.api.collection.playlist.contributor.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlaylistContributorDto(
    @SerialName("role")
    val role: String,
)