package com.diva.models.database.post

import com.diva.models.database.VisibilityEnum
import io.github.juevigrace.diva.core.models.Option

data class PostEntity(
    val id: String,
    val userId: String,
    val caption: Option<String> = Option.None,
    val description: Option<String> = Option.None,
    val visibility: VisibilityEnum,
    val createdAt: Long,
    val updatedAt: Long,
)
