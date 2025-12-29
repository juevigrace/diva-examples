package com.diva.models.database.collection.playlist

data class PlaylistSuggestionEntity(
    val collectionId: String,
    val suggesterId: String,
    val mediaId: String,
    val status: String,
    val suggestedAt: Long,
)
