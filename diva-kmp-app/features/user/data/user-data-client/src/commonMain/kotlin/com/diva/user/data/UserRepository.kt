package com.diva.user.data

import com.diva.models.Pagination
import com.diva.models.Repository
import com.diva.models.auth.SignUpForm
import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface UserRepository : Repository {
    fun getUsers(page: Int, pageSize: Int): Flow<DivaResult<Pagination<User>, DivaError>>

    @OptIn(ExperimentalUuidApi::class)
    fun getUserById(id: Uuid): Flow<DivaResult<User, DivaError>>

    fun createUser(form: SignUpForm): Flow<DivaResult<String, DivaError>>

    fun updateUser(user: User): Flow<DivaResult<Unit, DivaError>>

    fun requestEmailUpdate(email: String): Flow<DivaResult<Unit, DivaError>>

    fun confirmEmailUpdate(token: String): Flow<DivaResult<Unit, DivaError>>

    fun updateEmail(email: String): Flow<DivaResult<Unit, DivaError>>

    fun deleteUser(): Flow<DivaResult<Unit, DivaError>>
}
