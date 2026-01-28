package com.diva.social.data.post

import com.diva.database.social.post.PostStorage

class PostServiceImpl(
    private val storage: PostStorage,
) : PostService
