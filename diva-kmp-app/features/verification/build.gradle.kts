plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.core.database.databaseServer)
    implementation(projects.core.modelsServer)

    implementation(libs.koin.core)
}
