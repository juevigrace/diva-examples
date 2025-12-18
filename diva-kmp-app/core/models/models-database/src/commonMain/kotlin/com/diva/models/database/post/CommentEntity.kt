package com.diva.models.database.post

data class CommentEntity(
    val id: String,
    val userId: String,
    val postId: String,
    val content: String,
    val createdAt: Long,
    val updatedAt: Long,
)
