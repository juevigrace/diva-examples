plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.data.authDataServer)
        }
    }
}
