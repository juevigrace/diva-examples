package com.diva.models.session

enum class SessionStatus(
    val value: String,
) {
    ACTIVE("active"),
    EXPIRED("expired"),
    CLOSED("closed"),
}

fun safeSessionStatus(value: String): SessionStatus {
    return try {
        SessionStatus.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        SessionStatus.ACTIVE
    }
}
