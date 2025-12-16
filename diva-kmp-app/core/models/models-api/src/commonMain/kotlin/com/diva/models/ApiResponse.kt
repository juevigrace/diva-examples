package com.diva.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse<T>(
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String,
    @SerialName("timestamp")
    val timestamp: Long,
) {

}