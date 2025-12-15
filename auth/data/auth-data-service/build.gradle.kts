plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.auth.data.authDataShared)
        }
    }
}
