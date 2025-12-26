package com.diva.models.social.post

enum class PostType(
    val value: String,
) {
    MEDIA("media"),
    COLLECTION("collection"),
    TEXT("text"),
}

fun String.toPostType(): PostType =
    try {
        PostType.valueOf(this.uppercase())
    } catch (_: IllegalArgumentException) {
        PostType.MEDIA
    }
