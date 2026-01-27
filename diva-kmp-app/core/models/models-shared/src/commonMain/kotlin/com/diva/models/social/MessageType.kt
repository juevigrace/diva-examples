package com.diva.models.social

enum class MessageType {
    Text,
    Media,
    System,
    Unspecified,
}

fun safeMessageType(value: String): MessageType {
    return try {
        MessageType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        MessageType.Unspecified
    }
}
