package com.diva.models

import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaResult

abstract class DataSource<S>(protected open val source: S) {
    suspend fun<T> execute(block: suspend S.() -> DivaResult<T, DivaError>): DivaResult<T, DivaError> {
        return block(source)
    }
}
