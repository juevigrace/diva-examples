package com.diva.user.database.shared

import com.diva.models.user.User
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.database.Storage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserStorage : Storage<User> {
    suspend fun getByEmail(email: String): DivaResult<Option<User>, DivaError> {
        return DivaResult.failure(DivaError.exception(Exception("Server action only")))
    }

    suspend fun getByUsername(username: String): DivaResult<Option<User>, DivaError> {
        return DivaResult.failure(DivaError.exception(Exception("Server action only")))
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePassword(id: Uuid, passwordHash: String): DivaResult<Unit, DivaError> {
        return DivaResult.failure(DivaError.exception(Exception("Server action only")))
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateVerified(id: Uuid, verified: Boolean): DivaResult<Unit, DivaError> {
        return DivaResult.failure(DivaError.exception(Exception("Server action only")))
    }
}
