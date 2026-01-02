package com.diva.models.social

enum class ReactionType(
    val value: String,
) {
    LIKE("like"),
    COMMENT("comment"),
    BOOKMARK("bookmark"),
    SHARE("share"),
}

fun safeReactionType(value: String): ReactionType {
    return try {
        ReactionType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        ReactionType.LIKE
    }
}
