package com.diva.models.session

enum class SessionStatus(
    val value: String,
) {
    ACTIVE("active"),
    EXPIRED("expired"),
    CLOSED("closed"),
}

fun String.toSessionStatus(): SessionStatus =
    try {
        SessionStatus.valueOf(this.uppercase())
    } catch (_: IllegalArgumentException) {
        SessionStatus.ACTIVE
    }
