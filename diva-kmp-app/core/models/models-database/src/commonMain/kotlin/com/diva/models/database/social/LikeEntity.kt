package com.diva.models.database.social

data class LikeEntity(
    val id: String,
    val userId: String,
    val postId: String,
    val createdAt: Long,
)
