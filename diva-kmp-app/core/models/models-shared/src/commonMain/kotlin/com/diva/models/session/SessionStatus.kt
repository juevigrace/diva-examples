package com.diva.models.session

enum class SessionStatus {
    Active,
    Expired,
    Closed,
}

fun safeSessionStatus(value: String): SessionStatus {
    return try {
        SessionStatus.valueOf(value)
    } catch (_: IllegalArgumentException) {
        SessionStatus.Expired
    }
}
