plugins {
    id("divabuild.library")
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
            packageName.set("io.github.juevigrace.diva.core.database")
            schemaOutputDirectory.set(file("src/commonMain/sqldelight/databases"))
            generateAsync.set(true)
            deriveSchemaFromMigrations.set(true)
            verifyMigrations.set(true)
        }
    }
}
