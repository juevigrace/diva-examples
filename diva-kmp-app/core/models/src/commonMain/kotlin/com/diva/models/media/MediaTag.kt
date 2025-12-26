package com.diva.models.media

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class MediaTag(
    val id: Uuid,
    val tagName: String,
    val createdAt: Long,
)
