package com.diva.models.database.messaging.message

import io.github.juevigrace.diva.core.models.Option

data class MessageEntity(
    val id: String,
    val chatId: String,
    val senderId: String,
    val content: String,
    val messageType: String,
    val replyToId: Option<String> = Option.None,
    val editedAt: Option<Long> = Option.None,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
)
