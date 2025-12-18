import divabuild.internal.cleanPath
import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
}

group = "com.diva.${cleanPath()}"
version = libs.versions.app.version.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.diva.core)
        }
    }
}
