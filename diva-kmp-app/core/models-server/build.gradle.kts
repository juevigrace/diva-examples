plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.models)
            api(projects.core.models.modelsApi)

            implementation(libs.ktor.server.core)
        }
    }
}
