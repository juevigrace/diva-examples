package com.diva.user.data

import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError

interface UserRepository {
    suspend fun getUsers(): DivaResult<List<User>, DivaError>
}
