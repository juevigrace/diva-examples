package com.diva.models.database.social

data class FollowEntity(
    val id: String,
    val followerId: String,
    val followingId: String,
    val createdAt: Long,
)
