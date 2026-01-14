plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.core.modelsServer)

    api(projects.core.database.postgres)
    implementation(libs.diva.database)
}
