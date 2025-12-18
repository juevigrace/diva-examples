package com.diva.models.database.social

import io.github.juevigrace.diva.core.models.Option

data class SaveEntity(
    val id: String,
    val userId: String,
    val postId: String,
    val boardId: Option<String> = Option.None,
    val createdAt: Long,
)
