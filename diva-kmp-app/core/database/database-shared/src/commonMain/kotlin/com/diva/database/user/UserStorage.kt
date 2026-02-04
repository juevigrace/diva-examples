package com.diva.database.user

import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.ErrorCause
import io.github.juevigrace.diva.database.Storage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserStorage : Storage<User> {
    suspend fun getByEmail(email: String): DivaResult<Option<User>, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(Option.Some("database: server action only"))
            )
        )
    }

    suspend fun getByUsername(username: String): DivaResult<Option<User>, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(Option.Some("database: server action only"))
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateEmail(id: Uuid, email: String): DivaResult<Unit, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(Option.Some("database: server action only"))
            )
        )
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePassword(id: Uuid, passwordHash: String): DivaResult<Unit, DivaError> {
        return DivaResult.failure(
            DivaError(
                cause = ErrorCause.Error.NotImplemented(Option.Some("database: server action only"))
            )
        )
    }
}
