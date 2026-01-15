plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.media.database.mediaDatabaseServer)
    implementation(projects.features.media.data.mediaDataService)

    implementation(libs.koin.core)
}
