plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.modelsServer)

            api(projects.core.database.postgres)
            implementation(libs.diva.database)
        }
    }
}
