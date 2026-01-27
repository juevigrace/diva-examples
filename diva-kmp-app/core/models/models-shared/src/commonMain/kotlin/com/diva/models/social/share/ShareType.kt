package com.diva.models.social.share

enum class ShareType {
    Direct,
    External,
    Embed,
    Download,
    Unspecified,
}

fun safeShareType(value: String): ShareType {
    return try {
        ShareType.valueOf(value)
    } catch (_: IllegalArgumentException) {
        ShareType.Unspecified
    }
}
