plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.data.authDataService)
            implementation(projects.user.data.userDataService)

            implementation(projects.core.modelsServer)
            implementation(projects.core.server.util)

            implementation(libs.ktor.server.auth)
            implementation(libs.ktor.server.core)

            implementation(libs.koin.ktor)
        }
    }
}
