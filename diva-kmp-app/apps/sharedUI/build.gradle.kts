plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.database.sqlite)
            implementation(projects.core.ui)

            implementation(projects.features.auth.di.authDiClient)
            implementation(projects.features.user.di.userDiClient)

            implementation(libs.diva.network.client)
            api(libs.diva.di)
        }

        jvmMain.dependencies {
            api(libs.koin.logger.slf4j)
        }
    }
}
