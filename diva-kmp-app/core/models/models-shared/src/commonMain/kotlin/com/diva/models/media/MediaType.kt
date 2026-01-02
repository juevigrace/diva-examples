package com.diva.models.media

enum class MediaType(
    val value: String,
) {
    AUDIO("audio"),
    IMAGE("image"),
    VIDEO("video"),
    UNSPECIFIED("unspecified"),
}

fun safeMediaType(value: String): MediaType {
    return  try {
        MediaType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        MediaType.UNSPECIFIED
    }
}
