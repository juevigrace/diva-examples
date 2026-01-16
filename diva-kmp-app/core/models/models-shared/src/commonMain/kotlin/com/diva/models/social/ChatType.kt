package com.diva.models.social

enum class ChatType(
    val value: String,
) {
    DIRECT("direct"),
    GROUP("group"),
}

fun safeChatType(value: String): ChatType {
    return try {
        ChatType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        ChatType.DIRECT
    }
}
