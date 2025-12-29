package com.diva.models.database.messaging.chat

import io.github.juevigrace.diva.core.models.Option

data class ChatParticipantEntity(
    val chatId: String,
    val userId: String,
    val role: String,
    val joinedAt: Long,
    val lastReadAt: Option<Long> = Option.None,
    val mutedUntil: Option<Long> = Option.None,
    val addedBy: String,
)
