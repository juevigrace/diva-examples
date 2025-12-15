import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
}

val newPath = project.path.split(":").last().replace("-", ".")
val cleanPath = newPath.let { list ->
    val mutList = list.split(".").toMutableList()
    if (mutList.last() == "client" || mutList.last() == "server") {
        mutList.removeLast()
    }
    mutList.joinToString(".")
}
group = "com.diva.$cleanPath"
version = libs.versions.app.version.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.diva.core)
        }
    }
}
