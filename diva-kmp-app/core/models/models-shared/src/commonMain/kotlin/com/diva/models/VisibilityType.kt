package com.diva.models

enum class VisibilityType {
    Public,
    Private,
    Friends,
    Unspecified,
}

fun safeVisibilityType(value: String): VisibilityType {
    return try {
        VisibilityType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        VisibilityType.Unspecified
    }
}
