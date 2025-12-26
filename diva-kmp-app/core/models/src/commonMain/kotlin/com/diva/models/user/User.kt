package com.diva.models.user

import com.diva.models.collection.Collection
import com.diva.models.social.interaction.share.Share
import com.diva.models.social.post.Post
import io.github.juevigrace.diva.core.models.Option
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class User(
    val id: Uuid,
    val email: String,
    val username: String,
    val name: String,
    val alias: String,
    val avatar: String,
    val bio: String,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
    val followers: List<User> = emptyList(),
    val following: List<User> = emptyList(),
    val posts: List<Post> = emptyList(),
    val shares: List<Share> = emptyList(),
    val collections: List<Collection> = emptyList(),
)
