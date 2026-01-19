plugins {
    id("divabuild.library-server")
    alias(libs.plugins.sqldelight)
}

dependencies {
    api(libs.diva.database)
}

sqldelight {
    databases {
        create("DivaDB") {
            packageName.set("com.diva.database")
            schemaOutputDirectory.set(file("src/main/sqldelight/databases"))
            dialect(libs.sqldelight.postgres.dialect)
            generateAsync.set(true)
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)
        }
    }
}
