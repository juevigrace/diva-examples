package com.diva.app.config

data class AppConfig(
    val debug: Boolean = false,
    val flavor: Flavors = Flavors.MOCK,
    val domain: String = "localhost",
    val port: Int = 8080,
    val protocol: String = "http",
    val baseUrl: String = if (debug) "$protocol://$domain:$port" else "https://$domain",
)
