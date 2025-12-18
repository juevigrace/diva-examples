plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.api.authApiClient)

            api(projects.auth.data.authDataShared)
        }
    }
}
