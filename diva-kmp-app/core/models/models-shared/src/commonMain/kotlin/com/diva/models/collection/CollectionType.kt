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

fun String.toCollectionType(): CollectionType =
    try {
        CollectionType.valueOf(this.uppercase())
    } catch (_: IllegalArgumentException) {
        CollectionType.ALBUM
    }
