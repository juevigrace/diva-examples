package com.diva.models.database.collection

import com.diva.models.VisibilityType
import com.diva.models.collection.CollectionType
import io.github.juevigrace.diva.core.models.Option

data class CollectionEntity(
    val id: String,
    val owner: String,
    val coverMediaId: String,
    val name: String,
    val description: String,
    val collectionType: CollectionType,
    val visibility: VisibilityType,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
)
