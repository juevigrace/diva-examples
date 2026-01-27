package com.diva.social.data.interaction.comment

import com.diva.database.social.comment.CommentStorage

class CommentServiceImpl(
    private val storage: CommentStorage,
) : CommentService
