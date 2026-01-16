package com.diva.models.social

enum class ParticipantRole(
    val value: String,
) {
    ADMIN("admin"),
    MEMBER("member"),
}

fun safeParticipantRole(value: String): ParticipantRole {
    return try {
        ParticipantRole.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        ParticipantRole.MEMBER
    }
}
