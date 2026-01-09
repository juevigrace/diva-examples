package com.diva.user.data

import com.diva.models.user.User
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError

class UserRepositoryImpl : UserRepository {
    override suspend fun getUsers(): DivaResult<List<User>, DivaError> {
        TODO("Not yet implemented")
    }
}
