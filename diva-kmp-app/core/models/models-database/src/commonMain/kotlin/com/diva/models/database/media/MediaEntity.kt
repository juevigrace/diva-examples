package com.diva.models.database.media

import com.diva.models.VisibilityType
import com.diva.models.media.MediaType
import io.github.juevigrace.diva.core.models.Option

data class MediaEntity(
    val id: String,
    val submittedBy: String,
    val url: String,
    val altText: String,
    val mediaType: MediaType,
    val fileSize: Long,
    val width: Int,
    val height: Int,
    val duration: Int,
    val visibility: VisibilityType,
    val sensitiveContent: Boolean,
    val adultContent: Boolean,
    val publishedAt: Long,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
)
