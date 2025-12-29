package com.diva.models.database.social.follow

data class FollowEntity(
    val userId: String,
    val followed: String,
    val followedAt: Long,
)
