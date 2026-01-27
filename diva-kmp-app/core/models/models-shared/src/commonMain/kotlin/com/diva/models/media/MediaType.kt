package com.diva.models.media

enum class MediaType {
    Audio,
    Image,
    Video,
    Unspecified,
}

fun safeMediaType(value: String): MediaType {
    return try {
        MediaType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        MediaType.Unspecified
    }
}
