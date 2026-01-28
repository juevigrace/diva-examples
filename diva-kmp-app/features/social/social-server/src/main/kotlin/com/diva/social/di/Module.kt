package com.diva.social.di

import com.diva.database.social.comment.CommentStorage
import com.diva.database.social.interaction.InteractionStorage
import com.diva.database.social.post.PostStorage
import com.diva.database.social.share.ShareStorage
import com.diva.social.data.interaction.InteractionService
import com.diva.social.data.interaction.InteractionServiceImpl
import com.diva.social.data.interaction.comment.CommentService
import com.diva.social.data.interaction.comment.CommentServiceImpl
import com.diva.social.data.interaction.share.ShareService
import com.diva.social.data.interaction.share.ShareServiceImpl
import com.diva.social.data.post.PostService
import com.diva.social.data.post.PostServiceImpl
import com.diva.social.database.interaction.InteractionStorageImpl
import com.diva.social.database.interaction.comment.CommentStorageImpl
import com.diva.social.database.interaction.share.ShareStorageImpl
import com.diva.social.database.post.PostStorageImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun socialModule(): Module {
    return module {
        singleOf(::PostStorageImpl) { bind<PostStorage>() }
        singleOf(::PostServiceImpl) { bind<PostService>() }
        singleOf(::InteractionStorageImpl) { bind<InteractionStorage>() }
        singleOf(::InteractionServiceImpl) { bind<InteractionService>() }
        singleOf(::CommentStorageImpl) { bind<CommentStorage>() }
        singleOf(::CommentServiceImpl) { bind<CommentService>() }
        singleOf(::ShareStorageImpl) { bind<ShareStorage>() }
        singleOf(::ShareServiceImpl) { bind<ShareService>() }
    }
}
