package com.diva.models.database.user

import io.github.juevigrace.diva.core.models.Option

data class UserEntity(
    val id: String,
    val email: String,
    val username: String,
    val passwordHash: Option<String> = Option.None,
    val name: String,
    val alias: String,
    val avatar: String,
    val bio: String,
    val userVerified: Option<Boolean> = Option.None,
    val createdAt: Long,
    val updatedAt: Long,
    val deletedAt: Option<Long> = Option.None,
)
