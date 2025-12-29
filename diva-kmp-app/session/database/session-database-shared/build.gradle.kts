plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.models.modelsDatabase)

            implementation(libs.diva.database)
        }
    }
}
