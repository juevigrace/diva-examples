package com.diva.verification.database

import com.diva.models.server.UserVerification
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.database.DatabaseAction
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.database.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

interface VerificationStorage : Storage<UserVerification> {
    suspend fun getByToken(token: String): DivaResult<Option<UserVerification>, DivaError.DatabaseError>
    suspend fun deleteByToken(token: String): DivaResult<Unit, DivaError.DatabaseError>

    override suspend fun count(): DivaResult<Long, DivaError.DatabaseError> {
        return DivaResult.Failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Action not available"))
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<UserVerification>, DivaError.DatabaseError> {
        return DivaResult.Failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Action not available"))
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<UserVerification>, DivaError.DatabaseError>> {
        return flowOf(
            DivaResult.Failure(DivaError.DatabaseError(DatabaseAction.SELECT, details = "Action not available"))
        )
    }
}
