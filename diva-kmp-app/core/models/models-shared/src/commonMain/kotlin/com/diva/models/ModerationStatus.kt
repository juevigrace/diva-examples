package com.diva.models

enum class ModerationStatus(
    val value: String,
) {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected"),
    HIDDEN("hidden"),
}

fun safeModerationStatus(value: String): ModerationStatus {
    return try {
        ModerationStatus.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        ModerationStatus.PENDING
    }
}
