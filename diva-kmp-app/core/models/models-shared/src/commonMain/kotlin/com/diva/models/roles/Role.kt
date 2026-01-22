package com.diva.models.roles

enum class Role(val value: String) {
    ADMIN("admin"),
    USER("user"),
    MODERATOR("moderator")
}

fun safeRole(value: String): Role {
    return try {
        Role.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        Role.USER
    }
}
