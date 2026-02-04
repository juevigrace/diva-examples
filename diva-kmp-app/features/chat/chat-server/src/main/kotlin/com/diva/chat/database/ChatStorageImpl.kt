package com.diva.chat.database

import com.diva.database.chat.ChatStorage
import com.diva.models.chat.Chat
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ChatStorageImpl : ChatStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Chat>, DivaError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Chat>, DivaError>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Chat>, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Chat>, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Chat): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Chat): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }
}
