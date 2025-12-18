package divabuild.internal

import org.gradle.api.Project

fun Project.cleanPath(): String {
    val newPath = path.split(":").last().replace("-", ".")
    val cleanPath = newPath.let { path ->
        val mutList = path.split(".").toMutableList()
        if (mutList.contains("api") && mutList.contains("client")) {
            return@let mutList.joinToString(".")
        }
        if (mutList.last() == "client" || mutList.last() == "server") {
            mutList.removeAt(mutList.lastIndex)
        }
        mutList.joinToString(".")
    }
    return cleanPath
}
