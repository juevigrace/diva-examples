package com.diva.models.database.messaging.chat

import io.github.juevigrace.diva.core.models.Option

data class ChatEntity(
    val id: String,
    val createdBy: String,
    val chatType: String,
    val name: String,
    val description: String,
    val avatar: String,
    val lastMessageAt: Option<Long> = Option.None,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
)
