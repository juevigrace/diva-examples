package com.diva.models.database.collection

data class CollectionMediaEntity(
    val collectionId: String,
    val mediaId: String,
    val position: Int,
    val addedBy: String,
    val score: Double,
    val addedAt: Long,
)
