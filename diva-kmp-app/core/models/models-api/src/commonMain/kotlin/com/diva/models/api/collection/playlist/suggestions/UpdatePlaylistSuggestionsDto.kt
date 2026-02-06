package com.diva.models.api.collection.playlist.suggestions.dtos

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePlaylistSuggestionsDto(
    @SerialName("suggestion")
    val suggestion: String,
)