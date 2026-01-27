plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.features.app.settings.data.settingsData)

            implementation(libs.koin.core)
        }
    }
}
