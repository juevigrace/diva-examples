package com.diva.models.user

import com.diva.models.collection.Collection
import com.diva.models.social.interaction.share.Share
import com.diva.models.social.post.Post
import io.github.juevigrace.diva.core.Option
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
data class User(
    val id: Uuid,
    val email: String,
    val username: String,
    val passwordHash: Option<String> = Option.None,
    val alias: String = username,
    val avatar: String = "",
    val bio: String = "",
    val userVerified: Boolean = false,
    val createdAt: Instant,
    val updatedAt: Instant,
    val deletedAt: Option<Instant> = Option.None,
    val followers: List<User> = emptyList(),
    val following: List<User> = emptyList(),
    val posts: List<Post> = emptyList(),
    val shares: List<Share> = emptyList(),
    val collections: List<Collection> = emptyList(),
)
