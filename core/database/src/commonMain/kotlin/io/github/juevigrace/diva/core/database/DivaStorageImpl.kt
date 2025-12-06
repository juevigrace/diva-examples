package io.github.juevigrace.diva.core.database

import app.cash.sqldelight.db.SqlDriver
import io.github.juevigrace.diva.core.types.DivaError
import io.github.juevigrace.diva.core.types.DivaResult
import io.github.juevigrace.diva.database.Storage
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.Schema

class DivaStorageImpl(
    private val provider: DriverProvider
) : DivaStorage {
    private var storage: Storage<DivaDB>? = null

    override suspend fun <T> withStorage(
        block: suspend (storage: Storage<DivaDB>) -> DivaResult<T, DivaError>
    ): DivaResult<T, DivaError> {
        if (storage == null) {
            when (
                val result: DivaResult<SqlDriver, DivaError> =
                    provider.createDriver(Schema.Async(DivaDB.Schema))
            ) {
                is DivaResult.Failure -> {
                    return result
                }
                is DivaResult.Success -> {
                    storage = Storage(result.value, DivaDB(result.value))
                }
            }
        }
        return block(storage!!)
    }
}
