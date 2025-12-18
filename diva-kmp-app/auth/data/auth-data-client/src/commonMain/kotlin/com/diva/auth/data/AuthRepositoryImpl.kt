package com.diva.auth.data

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.auth.data.shared.AuthRepository
import com.diva.auth.database.shared.AuthStorage
import com.diva.models.LocalSource
import com.diva.models.RemoteRepository
import com.diva.models.RemoteSource

class AuthRepositoryImpl(
    override val local: LocalSource<AuthStorage>,
    override val remote: RemoteSource<AuthNetworkClient>
) : AuthRepository, RemoteRepository<AuthNetworkClient>
