package com.diva.user.database

import com.diva.database.DivaDB
import com.diva.models.user.User
import com.diva.user.database.shared.UserStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.isEmpty
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
import java.time.OffsetDateTime
import java.util.UUID
import kotlin.time.ExperimentalTime
import kotlin.time.toKotlinInstant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.uuid.toJavaUuid
import kotlin.uuid.toKotlinUuid

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
        return db.getOne { userQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<User>, DivaError>> {
        return db.getOneAsFlow { userQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    override suspend fun getByEmail(email: String): DivaResult<Option<User>, DivaError> {
        return db.getOne { userQueries.findOneByEmail(email, mapper = ::mapToEntity) }
    }

    override suspend fun getByUsername(username: String): DivaResult<Option<User>, DivaError> {
        return db.getOne { userQueries.findOneByUsername(username, mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun insert(item: User): DivaResult<Unit, DivaError> {
        return db.use {
            when {
                item.passwordHash.isEmpty() -> {
                    throw DivaErrorException(
                        DivaError.validation(
                            "passwordHash",
                            "required",
                            "Password is not present"
                        )
                    )
                }
                (item.passwordHash as Option.Some<String>).value.isEmpty() -> {
                    throw DivaErrorException(
                        DivaError.validation(
                            "passwordHash",
                            "required",
                            "Password is empty"
                        )
                    )
                }
            }
            transaction {
                userQueries.insert(
                    id = item.id.toJavaUuid(),
                    email = item.email,
                    username = item.username,
                    password_hash = (item.passwordHash as Option.Some<String>).value,
                    alias = item.alias,
                    avatar = item.avatar,
                    bio = item.bio,
                    user_verified = false,
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
                    id = item.id.toJavaUuid()
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateVerified(id: Uuid, verified: Boolean): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                userQueries.updateVerified(
                    user_verified = verified,
                    id = id.toJavaUuid()
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePassword(
        id: Uuid,
        passwordHash: String
    ): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                userQueries.updatePassword(
                    password_hash = passwordHash,
                    id = id.toJavaUuid()
                )
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        return db.use {
            transaction {
                userQueries.delete(id.toJavaUuid())
            }
            return@use DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    private fun mapToEntity(
        id: UUID,
        email: String,
        username: String,
        passwordHash: String?,
        alias: String,
        avatar: String,
        bio: String,
        userVerified: Boolean,
        createdAt: OffsetDateTime,
        updatedAt: OffsetDateTime,
        deletedAt: OffsetDateTime?,
    ): User {
        return User(
            id = id.toKotlinUuid(),
            email = email,
            username = username,
            passwordHash = Option.of(passwordHash),
            alias = alias,
            avatar = avatar,
            bio = bio,
            userVerified = userVerified,
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
