plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.media.database.mediaDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
