plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.core.modelsServer)

    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.auth.jwt)
    implementation(libs.kbcrypt)
    implementation(libs.kmail.core)
    implementation(libs.kmail.client)
}
