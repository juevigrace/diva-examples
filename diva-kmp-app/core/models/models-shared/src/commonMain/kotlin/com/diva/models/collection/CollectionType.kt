package com.diva.models.collection

enum class CollectionType(
    val value: String,
) {
    ALBUM("album"),
    PLAYLIST("playlist"),
    MIX("mix"),
    FAVORITES("favorites"),
    FEATURED("featured"),
    TRENDING("trending"),
}

fun safeCollectionType(value: String): CollectionType {
    return try {
        CollectionType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        CollectionType.ALBUM
    }
}
