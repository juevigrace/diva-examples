package com.diva.verification.database

import com.diva.database.DivaDB
import com.diva.models.server.UserVerification
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.database.DivaDatabase
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class VerificationStorageImpl(
    private val db: DivaDatabase<DivaDB>
) : VerificationStorage {
    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<UserVerification>, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<UserVerification>, DivaError>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<UserVerification>, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<UserVerification>, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: UserVerification): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: UserVerification): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }
}
