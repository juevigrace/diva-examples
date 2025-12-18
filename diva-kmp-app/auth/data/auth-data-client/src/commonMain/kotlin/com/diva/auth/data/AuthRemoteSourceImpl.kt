package com.diva.auth.data

import com.diva.auth.api.client.AuthNetworkClient
import com.diva.models.RemoteSource

class AuthRemoteSourceImpl(override val source: AuthNetworkClient) : RemoteSource<AuthNetworkClient>(source)
