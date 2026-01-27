package com.diva.models.roles

enum class Role {
    Admin,
    User,
    Moderator,
}

fun safeRole(value: String): Role {
    return try {
        Role.valueOf(value)
    } catch (_: IllegalArgumentException) {
        Role.User
    }
}
