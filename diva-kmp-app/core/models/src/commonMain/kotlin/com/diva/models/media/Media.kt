package com.diva.models.media

import com.diva.models.VisibilityType
import com.diva.models.media.tag.Tag
import com.diva.models.user.User
import io.github.juevigrace.diva.core.Option
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Media(
    val id: Uuid,
    val submittedBy: User,
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
    val tags: List<Tag> = emptyList(),
)
