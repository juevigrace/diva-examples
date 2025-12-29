package com.diva.models.database.social.interaction

import com.diva.models.social.ReactionType

data class InteractionEntity(
    val id: String,
    val userId: String,
    val postId: String,
    val reactionType: ReactionType,
    val createdAt: Long,
)
