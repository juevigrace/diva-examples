package com.diva.models

import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult

interface Repository<L> {
    val local: LocalSource<L>

    suspend fun<T> withLocal(block: suspend L.() -> DivaResult<T, DivaError>): DivaResult<T, DivaError> {
        return local.execute(block)
    }
}

interface RemoteRepository<R> {
    val remote: RemoteSource<R>
    suspend fun<T> withRemote(block: suspend R.() -> DivaResult<T, DivaError>): DivaResult<T, DivaError>{
        return remote.execute(block)
    }
}
