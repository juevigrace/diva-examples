plugins {
    id("divabuild.library-ui")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.database.databaseClient)
            implementation(projects.core.ui)

            implementation(projects.features.app.home.di.homeDi)
            implementation(projects.features.app.library.di.libraryDi)
            implementation(projects.features.app.profile.di.profileDi)
            implementation(projects.features.app.settings.di.settingsDi)

            implementation(projects.features.auth.di.authDiClient)
            implementation(projects.features.chat.di.chatDiClient)
            implementation(projects.features.collection.di.collectionDiClient)
            implementation(projects.features.media.di.mediaDiClient)
            implementation(projects.features.permissions.di.permissionsDiClient)
            implementation(projects.features.social.di.socialDiClient)
            implementation(projects.features.user.di.userDiClient)

            implementation(libs.diva.network.client)
            api(libs.diva.di)
        }

        jvmMain.dependencies {
            api(libs.koin.logger.slf4j)
        }
    }
}
