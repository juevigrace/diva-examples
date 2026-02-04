package com.diva.verification.api.handler

import com.diva.models.api.ApiResponse
import com.diva.models.api.user.dtos.EmailTokenDto
import com.diva.models.auth.Session
import com.diva.verification.data.VerificationService
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.onFailure
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi

interface VerificationHandler {
    suspend fun verifyEmailToken(dto: EmailTokenDto): DivaResult<ApiResponse<Unit>, DivaError>
    suspend fun verifyUserEmail(dto: EmailTokenDto, session: Session): DivaResult<ApiResponse<Unit>, DivaError>
}

class VerificationHandlerImpl(
    private val service: VerificationService,
) : VerificationHandler {
    private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun verifyEmailToken(
        dto: EmailTokenDto,
    ): DivaResult<ApiResponse<Unit>, DivaError> {
        return service.verify(dto.token).map {
            scope.launch { service.deleteToken(dto.token).onFailure { err -> println(err.message) } }
            ApiResponse(data = Unit, message = "email verified")
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun verifyUserEmail(
        dto: EmailTokenDto,
        session: Session
    ): DivaResult<ApiResponse<Unit>, DivaError> {
        return service.verifyUser(dto.token, session.user.id).map {
            scope.launch { service.deleteToken(dto.token).onFailure { err -> println(err.message) } }
            ApiResponse(data = Unit, message = "user verified")
        }
    }
}
