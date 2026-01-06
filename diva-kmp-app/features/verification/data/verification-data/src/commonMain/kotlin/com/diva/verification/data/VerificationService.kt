package com.diva.verification.data

import com.diva.models.server.UserVerification
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface VerificationService {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun createVerification(userId: Uuid): DivaResult<UserVerification, DivaError>

    @OptIn(ExperimentalUuidApi::class)
    suspend fun verify(token: String): DivaResult<Unit, DivaError>
}
