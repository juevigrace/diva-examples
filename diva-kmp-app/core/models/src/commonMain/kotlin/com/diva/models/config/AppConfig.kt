package com.diva.models.config

data class AppConfig(
    val debug: Boolean = true,
    val flavor: Flavors = Flavors.DEV,
    val domain: String = "localhost",
    val port: Int = 5000,
    val protocol: String = "http",
    val version: String = "1.0",
    val deviceName: String = "Unknown",
    val agent: String = "Diva/$version (Unknown)",
    val baseUrl: String = if (debug) "$protocol://$domain:$port" else "https://$domain",
)
