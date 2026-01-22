package com.diva.models.permission

import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalTime::class, ExperimentalUuidApi::class)
data class Permission(
    val id: Uuid,
    val name: String,
    val description: String,
    val createdAt: Instant,
    val updatedAt: Instant,
)
