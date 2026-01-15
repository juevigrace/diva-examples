plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.social.data.socialDataService)

    implementation(projects.core.modelsServer)
    implementation(projects.core.server.util)

    implementation(libs.ktor.server.auth)
    implementation(libs.ktor.server.core)

    implementation(libs.koin.ktor)
}
