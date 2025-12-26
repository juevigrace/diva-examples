package com.diva.models.media

enum class MediaType(
    val value: String,
) {
    AUDIO("audio"),
    IMAGE("image"),
    VIDEO("video"),
    UNSPECIFIED("unspecified"),
}

fun String.toMediaType(): MediaType =
    try {
        MediaType.valueOf(this.uppercase())
    } catch (_: IllegalArgumentException) {
        MediaType.UNSPECIFIED
    }
