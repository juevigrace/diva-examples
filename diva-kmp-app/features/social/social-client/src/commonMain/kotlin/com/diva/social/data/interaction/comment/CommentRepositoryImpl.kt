package com.diva.social.data.interaction.comment

import com.diva.database.social.comment.CommentStorage
import com.diva.social.api.client.interaction.comment.CommentClient

class CommentRepositoryImpl(
    private val client: CommentClient,
    private val storage: CommentStorage,
) : CommentRepository
