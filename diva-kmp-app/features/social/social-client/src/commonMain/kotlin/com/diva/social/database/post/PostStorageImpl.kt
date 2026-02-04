package com.diva.social.database.post

import com.diva.database.social.post.PostStorage
import com.diva.models.social.post.Post
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class PostStorageImpl : PostStorage {
    override suspend fun count(): DivaResult<Long, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Post>, DivaError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Post>, DivaError>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Post>, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Post>, DivaError>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Post): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Post): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }
}
