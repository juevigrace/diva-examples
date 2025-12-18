package com.diva.models.database.board

data class BoardPostEntity(
    val id: String,
    val boardId: String,
    val postId: String,
    val createdAt: Long,
)
