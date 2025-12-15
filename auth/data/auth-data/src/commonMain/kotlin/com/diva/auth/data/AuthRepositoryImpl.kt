package com.diva.auth.data

import com.diva.auth.database.shared.AuthStorage

class AuthRepositoryImpl(
    private val storage: AuthStorage,
    private val client: AuthNet,
) : AuthRepository
