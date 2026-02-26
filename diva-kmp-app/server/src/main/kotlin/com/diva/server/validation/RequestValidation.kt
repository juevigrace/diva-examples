package com.diva.server.validation

import com.diva.auth.api.validation.authRequestValidation
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig

fun RequestValidationConfig.appRequestValidation() {
    authRequestValidation()
}
