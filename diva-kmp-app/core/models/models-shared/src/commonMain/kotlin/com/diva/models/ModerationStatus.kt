package com.diva.models

enum class ModerationStatus {
    Pending,
    Approved,
    Rejected,
    Hidden,
    Unspecified,
}

fun safeModerationStatus(value: String): ModerationStatus {
    return try {
        ModerationStatus.valueOf(value)
    } catch (_: IllegalArgumentException) {
        ModerationStatus.Unspecified
    }
}
