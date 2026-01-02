plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.models)

            implementation(libs.diva.database)
        }
    }
}
