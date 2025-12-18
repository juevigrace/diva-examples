package com.diva.models.database.board

import com.diva.models.database.VisibilityEnum
import io.github.juevigrace.diva.core.models.Option

data class BoardEntity(
    val id: String,
    val userId: String,
    val name: String,
    val description: Option<String> = Option.None,
    val visibility: VisibilityEnum,
    val createdAt: Long,
    val updatedAt: Long,
)
