package com.diva.models.api.collection.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CollectionMediaResponse(
    @SerialName("collection_id")
    val collectionId: String,
    @SerialName("media_id")
    val mediaId: String,
    @SerialName("position")
    val position: Int,
    @SerialName("added_by_id")
    val addedById: String,
    @SerialName("score")
    val score: Float,
    @SerialName("added_at")
    val addedAt: Long,
)
