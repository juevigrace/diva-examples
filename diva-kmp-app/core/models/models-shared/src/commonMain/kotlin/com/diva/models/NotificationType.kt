package com.diva.models

enum class NotificationType {
    Like,
    Comment,
    Reply,
    Share,
    Follow,
    Mention,
    Unspecified,
}

fun safeNotificationType(value: String): NotificationType {
    return try {
        NotificationType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        NotificationType.Unspecified
    }
}
