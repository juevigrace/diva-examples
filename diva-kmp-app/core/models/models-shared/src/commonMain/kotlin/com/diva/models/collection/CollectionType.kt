package com.diva.models.collection

enum class CollectionType {
    Album,
    Playlist,
    Mix,
    Favorites,
    Featured,
    Trending,
    Unspecified,
}

fun safeCollectionType(value: String): CollectionType {
    return try {
        CollectionType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        CollectionType.Unspecified
    }
}
