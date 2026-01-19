plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.core.modelsServer)

    api(projects.core.database.databaseServer)
    implementation(libs.diva.database)
}
