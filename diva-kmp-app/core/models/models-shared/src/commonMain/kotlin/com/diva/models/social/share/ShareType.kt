package com.diva.models.social.share

enum class ShareType(
    val value: String,
) {
    DIRECT("direct"),
    EXTERNAL("external"),
    EMBED("embed"),
    DOWNLOAD("download"),
}

fun safeShareType(value: String): ShareType {
    return try {
        ShareType.valueOf(value.uppercase())
    } catch (_: IllegalArgumentException) {
        ShareType.DIRECT
    }
}
