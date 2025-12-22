package com.diva.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Serializable
data class ApiResponse<T>
@OptIn(ExperimentalTime::class)
constructor(
    @SerialName("data")
    val data: T? = null,
    @SerialName("message")
    val message: String,
    @SerialName("timestamp")
    val timestamp: Long = Clock.System.now().toEpochMilliseconds(),
)
