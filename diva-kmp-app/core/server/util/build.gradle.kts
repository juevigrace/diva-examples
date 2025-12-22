plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.modelsServer)

            implementation(libs.ktor.server.auth.jwt)
            implementation(libs.kbcrypt)
        }
    }
}
