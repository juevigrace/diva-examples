package com.diva.models

interface Validator<F, T> {
    fun validate(form: F): T
}

interface ValidationResult {
    val hasErrors: Boolean
    fun valid(): Boolean
}
