package com.diva.models

enum class VisibilityType(
    val value: String,
) {
    PUBLIC("public"),
    PRIVATE("private"),
    FRIENDS("friends"),
    UNSPECIFIED("unspecified"),
}

fun safeVisibilityType(value: String): VisibilityType {
    return try {
        VisibilityType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        VisibilityType.UNSPECIFIED
    }
}
