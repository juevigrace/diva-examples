package com.diva.user.database

import com.diva.database.DivaDB
import com.diva.database.user.UserStorage
import com.diva.models.roles.Role
import com.diva.models.roles.safeRole
import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.fold
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
    override suspend fun count(): DivaResult<Long, DivaError.DatabaseError> {
        return db.use {
            val value: Long = userQueries.count().executeAsOne()
            DivaResult.success(value)
        }
    }

    override suspend fun getAll(limit: Int, offset: Int): DivaResult<List<User>, DivaError.DatabaseError> {
        return db.getList { userQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    override fun getAllFlow(limit: Int, offset: Int): Flow<DivaResult<List<User>, DivaError.DatabaseError>> {
        return db.getListAsFlow { userQueries.findAll(limit.toLong(), offset.toLong(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<User>, DivaError.DatabaseError> {
        return db.getOne { userQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<User>, DivaError.DatabaseError>> {
        return db.getOneAsFlow { userQueries.findOneById(id.toJavaUuid(), mapper = ::mapToEntity) }
    }

    override suspend fun getByEmail(email: String): DivaResult<Option<User>, DivaError.DatabaseError> {
        return db.getOne { userQueries.findOneByEmail(email, mapper = ::mapToEntity) }
    }

    override suspend fun getByUsername(username: String): DivaResult<Option<User>, DivaError.DatabaseError> {
        return db.getOne { userQueries.findOneByUsername(username, mapper = ::mapToEntity) }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun insert(item: User): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                val password: String = item.passwordHash.fold(
                    onNone = {
                        rollback(-1)
                    },
                    onSome = { value -> value }
                )
                userQueries.insert(
                    id = item.id.toJavaUuid(),
                    email = item.email,
                    username = item.username,
                    password_hash = password,
                    alias = item.alias,
                    avatar = item.avatar,
                    bio = item.bio,
                    user_verified = false,
                    role = Role.USER.name
                )
            }
            if (rows.toInt() == -1 || rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError.DatabaseError(
                        operation = DatabaseAction.INSERT,
                        table = "diva_user",
                        details = "No rows affected"
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun update(item: User): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.update(
                    email = item.email,
                    username = item.username,
                    alias = item.alias,
                    avatar = item.avatar,
                    bio = item.bio,
                    id = item.id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError.DatabaseError(
                        operation = DatabaseAction.UPDATE,
                        table = "diva_user",
                        details = "No rows affected"
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateEmail(
        id: Uuid,
        email: String
    ): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.updateEmail(
                    email = email,
                    id = id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError.DatabaseError(
                        operation = DatabaseAction.UPDATE,
                        table = "diva_user",
                        details = "No rows affected"
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updateVerified(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.updateVerified(
                    user_verified = true,
                    id = id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError.DatabaseError(
                        operation = DatabaseAction.UPDATE,
                        table = "diva_user",
                        details = "No rows affected"
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun updatePassword(
        id: Uuid,
        passwordHash: String
    ): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.updatePassword(
                    password_hash = passwordHash,
                    id = id.toJavaUuid()
                )
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError.DatabaseError(
                        operation = DatabaseAction.UPDATE,
                        table = "diva_user",
                        details = "No rows affected"
                    )
                )
            }
            DivaResult.success(Unit)
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        return db.use {
            val rows: Long = transactionWithResult {
                userQueries.delete(id.toJavaUuid())
            }
            if (rows.toInt() == 0) {
                return@use DivaResult.failure(
                    DivaError.DatabaseError(
                        operation = DatabaseAction.DELETE,
                        table = "diva_user",
                        details = "No rows affected"
                    )
                )
            }
            DivaResult.success(Unit)
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
        role: String,
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
            role = safeRole(role),
            createdAt = createdAt.toInstant().toKotlinInstant(),
            updatedAt = updatedAt.toInstant().toKotlinInstant(),
            deletedAt = Option.of(deletedAt?.toInstant()?.toKotlinInstant()),
        )
    }
}
