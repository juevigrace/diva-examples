plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.data.authDataService)

            implementation(projects.core.models.modelsApi)
            implementation(projects.core.modelsServer)

            implementation(libs.ktor.server.auth)
            implementation(libs.ktor.server.core)

            implementation(libs.koin.ktor)
        }
    }
}
