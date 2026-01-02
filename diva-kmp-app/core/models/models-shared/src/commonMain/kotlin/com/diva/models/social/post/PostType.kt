package com.diva.models.social.post

enum class PostType(
    val value: String,
) {
    MEDIA("media"),
    COLLECTION("collection"),
    TEXT("text"),
}

fun safePostType(value: String): PostType {
    return try {
        PostType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        PostType.MEDIA
    }
}
