package com.diva.app.config

data class AppConfig(
    val debug: Boolean = false,
    val flavor: Flavors = Flavors.DEV,
    val domain: String = "localhost",
    val port: Int = 5000,
    val protocol: String = "http",
    val baseUrl: String = if (debug) "$protocol://$domain:$port" else "https://$domain",
)
