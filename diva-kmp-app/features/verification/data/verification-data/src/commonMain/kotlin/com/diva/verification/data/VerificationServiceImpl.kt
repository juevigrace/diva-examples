package com.diva.verification.data

import com.diva.models.api.user.dtos.VerifyEmailDto
import com.diva.verification.database.VerificationStorage
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class VerificationServiceImpl(
    private val storage: VerificationStorage
) : VerificationService {
    @OptIn(ExperimentalUuidApi::class)
    override suspend fun createVerification(userId: Uuid): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }

    override suspend fun verify(dto: VerifyEmailDto): DivaResult<Unit, DivaError> {
        TODO("Not yet implemented")
    }
}
