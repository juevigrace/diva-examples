package com.diva.models.database.user

import io.github.juevigrace.diva.core.models.Option

data class UserEntity(
    val id: String,
    val email: String,
    val username: String,
    val passwordHash: Option<String> = Option.None,
    val name: String,
    val alias: String,
    val avatar: Option<String> = Option.None,
    val bio: Option<String> = Option.None,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        fun clientEntity(
            id: String,
            email: String,
            username: String,
            name: String,
            alias: String,
            avatar: Option<String> = Option.None,
            bio: Option<String> = Option.None,
            createdAt: Long,
            updatedAt: Long,
        ): UserEntity {
            return UserEntity(
                id = id,
                email = email,
                username = username,
                name = name,
                alias = alias,
                avatar = avatar,
                bio = bio,
                createdAt = createdAt,
                updatedAt = updatedAt
            )
        }
    }
}
}
