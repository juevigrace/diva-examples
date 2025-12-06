package io.github.juevigrace.diva.core.database

import io.github.juevigrace.diva.core.types.DivaError
import io.github.juevigrace.diva.core.types.DivaResult
import io.github.juevigrace.diva.database.Storage

interface DivaStorage {
    suspend fun<T> withStorage(
        block: suspend (storage: Storage<DivaDB>) -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError>
}
