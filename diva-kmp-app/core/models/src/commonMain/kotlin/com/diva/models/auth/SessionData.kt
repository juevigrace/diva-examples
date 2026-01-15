package com.diva.models.auth

import com.diva.models.api.auth.dtos.SessionDataDto

data class SessionData(
    val device: String = "",
    val ip: String = "",
    val agent: String = "",
) {
    fun toSessionDataDto(): SessionDataDto {
        return SessionDataDto(
            device = device,
            ipAddress = ip,
            userAgent = agent
        )
    }
}
