package com.diva.auth.api.client

import com.diva.models.auth.dtos.SignInDto
import com.diva.models.auth.response.AuthResponse
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult

interface AuthNetworkClient {
    suspend fun signIn(dto: SignInDto): DivaResult<AuthResponse, DivaError>
}
