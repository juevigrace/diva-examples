package com.diva.models.auth

import com.diva.models.api.auth.dtos.SignUpDto
import com.diva.models.api.user.dtos.CreateUserDto

data class SignUpForm(
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val alias: String = username,
    val avatar: String = "",
    val bio: String = "",
    val sessionData: SessionData = SessionData(),
) {
    fun toSignUpDto(): SignUpDto {
        return SignUpDto(
            user = CreateUserDto(
                email = email,
                username = username,
                password = password,
                alias = alias,
                avatar = avatar,
                bio = bio,
            ),
            sessionData = sessionData.toSessionDataDto()
        )
    }
}
