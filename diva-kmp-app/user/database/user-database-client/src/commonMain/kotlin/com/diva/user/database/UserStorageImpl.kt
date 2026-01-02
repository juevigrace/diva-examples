package com.diva.user.database

import com.diva.database.DivaDB
import com.diva.models.user.User
import com.diva.user.database.shared.UserStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class UserStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : UserStorage {
    override suspend fun getAll(limit: Int, offset: Int): DivaResult<List<User>, DivaError> {
        return db.getList { userQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override suspend fun getAllFlow(limit: Int, offset: Int): Flow<DivaResult<List<User>, DivaError>> {
        return db.getListAsFlow { userQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<User>, DivaError> {
        return db.getOne { userQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<User>, DivaError>> {
        return db.getOneAsFlow { userQueries.findOneById(id.toString(), mapper = ::mapToEntity) }
    }

    override suspend fun getByUsername(username: String): DivaResult<Option<User>, DivaError> {
        return db.getOne { userQueries.findOneByUsername(username, mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun insert(item: User): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                userQueries.insert(
                    id = item.id.toString(),
                    email = item.email,
                    username = item.username,
                    alias = item.alias,
                    avatar = item.avatar,
                    bio = item.bio,
                    user_verified = item.userVerified,
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun update(item: User): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                userQueries.update(
                    email = item.email,
                    username = item.username,
                    alias = item.alias,
                    avatar = item.avatar,
                    bio = item.bio,
                    id = item.id.toString()
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                userQueries.delete(id.toString())
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    private fun mapToEntity(
        id: String,
        email: String,
        username: String,
        alias: String,
        avatar: String,
        bio: String,
        userVerified: Boolean,
        createdAt: Long,
        updatedAt: Long,
        deletedAt: Long?,
    ): User {
        return User(
            id = Uuid.parse(id),
            email = email,
            username = username,
            alias = alias,
            avatar = avatar,
            bio = bio,
            userVerified = userVerified,
            createdAt = Instant.fromEpochMilliseconds(createdAt),
            updatedAt = Instant.fromEpochMilliseconds(updatedAt),
            deletedAt = Option.of(deletedAt?.let { Instant.fromEpochMilliseconds(it) }),
        )
    }
}
