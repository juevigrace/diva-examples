plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.database)
            implementation(projects.core.types)
            implementation(projects.core.ui)

            api(libs.diva.di)

            api(libs.koin.core)
            api(libs.koin.compose)
            api(libs.koin.compose.viewmodel)
        }

        androidMain.dependencies {
            api(libs.koin.android)
            api(libs.koin.androidx.compose)
        }

        jvmMain.dependencies {
            api(libs.koin.logger.slf4j)
        }
    }
}
