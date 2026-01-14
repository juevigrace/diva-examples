plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.auth.data.authDataService)

    implementation(projects.features.session.database.sessionDatabaseServer)

    implementation(libs.koin.core)
}
