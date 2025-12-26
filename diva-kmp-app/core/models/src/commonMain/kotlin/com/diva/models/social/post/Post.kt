package com.diva.models.social.post

import com.diva.models.VisibilityType
import com.diva.models.collection.Collection
import com.diva.models.media.Media
import com.diva.models.user.User
import io.github.juevigrace.diva.core.models.Option
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Post(
    val id: Uuid,
    val author: User,
    val type: PostType,
    val title: String,
    val content: String,
    val media: Option<Media>,
    val collection: Option<Collection>,
    val visibility: VisibilityType,
    val editedAt: Option<Long>,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long>,
)
