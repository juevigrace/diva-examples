package com.diva.social.database.interaction

import com.diva.database.social.interaction.InteractionStorage
import com.diva.models.social.interaction.Interaction
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class InteractionStorageImpl : InteractionStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Interaction>, DivaError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Interaction>, DivaError>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Interaction>, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Interaction>, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Interaction): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Interaction): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }
}
