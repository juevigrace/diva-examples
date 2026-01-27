package com.diva.models.social

enum class ReactionType {
    Like,
    Comment,
    Bookmark,
    Share,
    Unspecified,
}

fun safeReactionType(value: String): ReactionType {
    return try {
        ReactionType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        ReactionType.Unspecified
    }
}
