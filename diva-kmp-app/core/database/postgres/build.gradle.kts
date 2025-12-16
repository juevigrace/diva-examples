plugins {
    id("divabuild.library-server")
    alias(libs.plugins.sqldelight)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.diva.database)
        }
    }
}

sqldelight {
    databases {
        create("DivaDB") {
            packageName.set("com.diva.database")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            dialect(libs.sqldelight.postgres.dialect)
            generateAsync.set(true)
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)
        }
    }
}
