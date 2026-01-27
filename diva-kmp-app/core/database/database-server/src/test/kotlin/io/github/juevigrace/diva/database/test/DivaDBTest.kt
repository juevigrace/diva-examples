package io.github.juevigrace.diva.database.test

import app.cash.sqldelight.db.SqlDriver
import com.diva.database.DivaDB
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.isSuccess
import io.github.juevigrace.diva.database.DivaDatabase
import io.github.juevigrace.diva.database.driver.DriverProvider
import io.github.juevigrace.diva.database.driver.Schema
import io.github.juevigrace.diva.database.driver.configuration.DriverConf
import io.github.juevigrace.diva.database.driver.configuration.JvmConf
import io.github.juevigrace.diva.database.driver.factory.JvmDriverProviderFactory
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

class DivaDBTest {
    companion object {
        private val provider: DriverProvider = JvmDriverProviderFactory(
            JvmConf(
                DriverConf.PostgresqlDriverConf(
                    host = "postgres-container",
                    port = 5433,
                    username = "postgres",
                    password = "postgres",
                    database = "pg",
                    schema = "public",
                )
            )
        ).create()
        private val db: DivaDatabase<DivaDB> by lazy {
            val result = provider.createDriver(Schema.Sync(DivaDB.Schema))
            assert(result.isSuccess()) {
                "INITIALIZATION ERROR: ${(result as DivaResult.Failure).err}"
            }

            val success: SqlDriver = (result as DivaResult.Success<SqlDriver>).value
            DivaDatabase(success, DivaDB(success))
        }
    }

    @Test
    fun `test check health`() = runTest {
        val check = db.checkHealth()
        assert(check.isSuccess()) {
            "CHECK HEALTH ERROR: ${(check as DivaResult.Failure).err}"
        }
    }
}
