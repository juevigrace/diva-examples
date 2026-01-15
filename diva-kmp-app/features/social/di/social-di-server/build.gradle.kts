plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.social.database.socialDatabaseServer)
    implementation(projects.features.social.data.socialDataService)

    implementation(libs.koin.core)
}
