package com.diva.models

import kotlinx.serialization.Serializable

sealed interface ResponseType {
    data class Type<T>(@Serializable val data: T) : ResponseType
}
