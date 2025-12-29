package com.diva.models.database.collection.playlist

data class PlaylistMetadataEntity(
    val collectionId: String,
    val isCollaborative: Boolean,
    val allowSuggestions: Boolean,
)
