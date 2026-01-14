package com.diva.verification.data

import com.diva.models.server.UserVerification
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface VerificationService {
    @OptIn(ExperimentalUuidApi::class)
    suspend fun createVerification(userId: Uuid): DivaResult<UserVerification, DivaError>

    // TODO: should this need a session?
    @OptIn(ExperimentalUuidApi::class)
    suspend fun verify(token: String): DivaResult<Unit, DivaError>
}
