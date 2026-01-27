package com.diva.models.social

enum class ParticipantRole {
    Admin,
    Member,
    Unspecified,
}

fun safeParticipantRole(value: String): ParticipantRole {
    return try {
        ParticipantRole.valueOf(value)
    } catch (_: IllegalArgumentException) {
        ParticipantRole.Unspecified
    }
}
