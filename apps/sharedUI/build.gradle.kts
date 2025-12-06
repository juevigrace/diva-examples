plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            api(libs.koin.android)
            api(libs.koin.androidx.compose)
        }

        commonMain.dependencies {
            implementation(projects.core.database)

            implementation(libs.kotlinx.serialization.json)

            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
        }
    }
}
