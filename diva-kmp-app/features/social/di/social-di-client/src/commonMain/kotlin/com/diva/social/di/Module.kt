package com.diva.social.di

import com.diva.database.social.comment.CommentStorage
import com.diva.database.social.interaction.InteractionStorage
import com.diva.database.social.post.PostStorage
import com.diva.database.social.share.ShareStorage
import com.diva.social.api.client.interaction.InteractionClient
import com.diva.social.api.client.interaction.InteractionClientImpl
import com.diva.social.api.client.interaction.comment.CommentClient
import com.diva.social.api.client.interaction.comment.CommentClientImpl
import com.diva.social.api.client.interaction.share.ShareClient
import com.diva.social.api.client.interaction.share.ShareClientImpl
import com.diva.social.api.client.post.PostClient
import com.diva.social.api.client.post.PostClientImpl
import com.diva.social.data.interaction.InteractionRepository
import com.diva.social.data.interaction.InteractionRepositoryImpl
import com.diva.social.data.interaction.comment.CommentRepository
import com.diva.social.data.interaction.comment.CommentRepositoryImpl
import com.diva.social.data.interaction.share.ShareRepository
import com.diva.social.data.interaction.share.ShareRepositoryImpl
import com.diva.social.data.post.PostRepository
import com.diva.social.data.post.PostRepositoryImpl
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
        singleOf(::PostClientImpl) { bind<PostClient>() }
        singleOf(::PostStorageImpl) { bind<PostStorage>() }
        singleOf(::PostRepositoryImpl) { bind<PostRepository>() }

        singleOf(::InteractionClientImpl) { bind<InteractionClient>() }
        singleOf(::InteractionStorageImpl) { bind<InteractionStorage>() }
        singleOf(::InteractionRepositoryImpl) { bind<InteractionRepository>() }

        singleOf(::CommentClientImpl) { bind<CommentClient>() }
        singleOf(::CommentStorageImpl) { bind<CommentStorage>() }
        singleOf(::CommentRepositoryImpl) { bind<CommentRepository>() }

        singleOf(::ShareClientImpl) { bind<ShareClient>() }
        singleOf(::ShareStorageImpl) { bind<ShareStorage>() }
        singleOf(::ShareRepositoryImpl) { bind<ShareRepository>() }
    }
}
