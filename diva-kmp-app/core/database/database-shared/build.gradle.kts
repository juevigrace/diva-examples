plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.models)

            api(libs.diva.database)
        }
    }
}
