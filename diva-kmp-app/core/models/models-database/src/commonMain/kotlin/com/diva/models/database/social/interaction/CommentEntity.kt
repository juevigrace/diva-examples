package com.diva.models.database.social.interaction

import io.github.juevigrace.diva.core.models.Option

data class CommentEntity(
    val interactionId: String,
    val replyTo: Option<String> = Option.None,
    val content: String,
    val editedAt: Option<Long> = Option.None,
)
