package com.diva.models

enum class VisibilityType(
    val value: String,
) {
    PUBLIC("public"),
    PRIVATE("private"),
    FRIENDS("friends"),
    UNSPECIFIED("unspecified"),
}

fun String.toVisibilityType(): VisibilityType =
    try {
        VisibilityType.valueOf(this.uppercase())
    } catch (_: IllegalArgumentException) {
        VisibilityType.UNSPECIFIED
    }
