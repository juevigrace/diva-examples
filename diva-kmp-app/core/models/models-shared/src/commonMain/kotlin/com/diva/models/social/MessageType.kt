package com.diva.models.social

enum class MessageType(
    val value: String,
) {
    TEXT("text"),
    MEDIA("media"),
    SYSTEM("system"),
}

fun safeMessageType(value: String): MessageType {
    return try {
        MessageType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        MessageType.TEXT
    }
}
