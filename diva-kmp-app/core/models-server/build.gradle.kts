plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.models)

            implementation(libs.ktor.server.core)
        }
    }
}
