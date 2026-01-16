package com.diva.models

enum class NotificationType(
    val value: String,
) {
    LIKE("like"),
    COMMENT("comment"),
    REPLY("reply"),
    SHARE("share"),
    FOLLOW("follow"),
    MENTION("mention"),
}

fun safeNotificationType(value: String): NotificationType {
    return try {
        NotificationType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        NotificationType.LIKE
    }
}
