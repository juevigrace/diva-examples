package com.diva.models.social

enum class ChatType {
    Direct,
    Group,
    Unspecified,
}

fun safeChatType(value: String): ChatType {
    return try {
        ChatType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        ChatType.Unspecified
    }
}
