package com.diva.auth.data.service

import com.diva.auth.data.shared.AuthRepository
import com.diva.auth.database.shared.AuthStorage
import com.diva.models.LocalSource
import com.diva.models.ResponseType
import com.diva.models.auth.dtos.SignInDto
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.toDivaError
import io.github.juevigrace.diva.core.models.tryResult

class AuthRepositoryImpl(
    override val local: LocalSource<AuthStorage>,
) : AuthRepository {

}
