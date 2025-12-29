package com.diva.models.database.tag

import io.github.juevigrace.diva.core.models.Option

data class TagEntity(
    val id: String,
    val tagName: String,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
)
