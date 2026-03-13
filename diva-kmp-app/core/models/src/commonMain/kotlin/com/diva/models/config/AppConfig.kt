package com.diva.models.config

import io.github.juevigrace.diva.core.Option
import io.github.juevigrace.diva.core.config.DivaAppConfig
import io.github.juevigrace.diva.core.config.Environment

data class AppConfig(
    override val debug: Boolean = true,
    override val environment: Environment = Environment.DEVELOPMENT,
    override val domain: String = "localhost",
    override val port: Option<Int> = Option.Some(5000),
    val protocol: String = if (environment == Environment.DEVELOPMENT) {
        "http"
    } else {
        "https"
    },
    override val version: String = "1.0",
    override val deviceName: String = "Unknown",
    override val agent: String = "Diva/$version (Unknown)",
    override val baseUrl: String = if (environment == Environment.DEVELOPMENT) {
        "$protocol://$domain:$port"
    } else {
        "https://$domain"
    },
) : DivaAppConfig
