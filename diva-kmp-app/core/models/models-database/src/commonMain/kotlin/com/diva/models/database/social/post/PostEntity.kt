package com.diva.models.database.social.post

import com.diva.models.VisibilityType
import com.diva.models.social.post.PostType
import io.github.juevigrace.diva.core.models.Option

data class PostEntity(
    val id: String,
    val authorId: String,
    val postType: PostType,
    val title: String,
    val content: String,
    val mediaId: Option<String> = Option.None,
    val collectionId: Option<String> = Option.None,
    val visibility: VisibilityType,
    val editedAt: Option<Long> = Option.None,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
)
