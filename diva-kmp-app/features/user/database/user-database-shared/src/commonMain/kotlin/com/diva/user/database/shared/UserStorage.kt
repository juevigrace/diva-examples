package com.diva.user.database.shared

import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.database.Storage
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserStorage : Storage<User> {
    suspend fun getByEmail(email: String): DivaResult<Option<User>, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Server action only"))
    }

    suspend fun getByUsername(username: String): DivaResult<Option<User>, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Server action only"))
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateEmail(id: Uuid, email: String): DivaResult<Unit, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.UPDATE, details = "Server action only"))
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updatePassword(id: Uuid, passwordHash: String): DivaResult<Unit, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.UPDATE, details = "Server action only"))
    }

    @OptIn(ExperimentalUuidApi::class)
    suspend fun updateVerified(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        return DivaResult.failure(DivaError.DatabaseError(DatabaseAction.UPDATE, details = "Server action only"))
    }
}
