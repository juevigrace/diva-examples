package com.diva.models.social.share

enum class ShareType(
    val value: String,
) {
    DIRECT("direct"),
    EXTERNAL("external"),
    EMBED("embed"),
    DOWNLOAD("download"),
}

fun String.toShareType(): ShareType =
    try {
        ShareType.valueOf(this.uppercase())
    } catch (_: IllegalArgumentException) {
        ShareType.DIRECT
    }
