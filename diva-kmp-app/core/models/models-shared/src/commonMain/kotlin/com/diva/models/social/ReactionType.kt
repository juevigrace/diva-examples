package com.diva.models.social

enum class ReactionType(
    val value: String,
) {
    LIKE("like"),
    COMMENT("comment"),
    BOOKMARK("bookmark"),
    SHARE("share"),
}

fun String.toReactionType(): ReactionType =
    try {
        ReactionType.valueOf(this.uppercase())
    } catch (_: IllegalArgumentException) {
        ReactionType.LIKE
    }
