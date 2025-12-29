package com.diva.models.database.messaging.message

data class MessageMediaEntity(
    val messageId: String,
    val mediaId: String,
    val position: Int,
)
