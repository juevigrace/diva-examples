package com.diva.models

sealed interface ResponseType {
    data class Type<T>(val data: T) : ResponseType
}
