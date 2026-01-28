package com.diva.collection.database

import com.diva.database.collection.CollectionStorage
import com.diva.models.collection.Collection
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CollectionStorageImpl : CollectionStorage {
    override suspend fun count(): DivaResult<Long, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Collection>, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Collection>, DivaError.DatabaseError>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Collection>, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Collection>, DivaError.DatabaseError>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Collection): DivaResult<Unit, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Collection): DivaResult<Unit, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }
}
