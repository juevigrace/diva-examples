package com.diva.models.database.post

import com.diva.models.database.MediaTypeEnum
import io.github.juevigrace.diva.core.models.Option

data class MediaEntity(
    val id: String,
    val postId: String,
    val url: String,
    val altText: Option<String> = Option.None,
    val mediaType: MediaTypeEnum,
    val fileSize: Option<Long> = Option.None,
    val width: Option<Int> = Option.None,
    val height: Option<Int> = Option.None,
    val createdAt: Long,
)
