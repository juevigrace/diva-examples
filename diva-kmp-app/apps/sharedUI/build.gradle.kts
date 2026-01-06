plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.database.sqlite)
            implementation(projects.core.ui)

            implementation(projects.features.auth.di.authDiClient)

            api(libs.diva.di)
        }

        jvmMain.dependencies {
            api(libs.koin.logger.slf4j)
        }
    }
}
