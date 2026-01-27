package com.diva.social.database.interaction.comment

import com.diva.database.social.comment.CommentStorage
import com.diva.models.social.interaction.comment.Comment
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class CommentStorageImpl : CommentStorage {
    override suspend fun count(): DivaResult<Long, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    override suspend fun getAll(
        limit: Int,
        offset: Int
    ): DivaResult<List<Comment>, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    override fun getAllFlow(
        limit: Int,
        offset: Int
    ): Flow<DivaResult<List<Comment>, DivaError.DatabaseError>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun getById(id: Uuid): DivaResult<Option<Comment>, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override fun getByIdFlow(id: Uuid): Flow<DivaResult<Option<Comment>, DivaError.DatabaseError>> {
        TODO("Not yet implemented")
    }

    override suspend fun insert(item: Comment): DivaResult<Unit, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    override suspend fun update(item: Comment): DivaResult<Unit, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun delete(id: Uuid): DivaResult<Unit, DivaError.DatabaseError> {
        TODO("Not yet implemented")
    }
}
