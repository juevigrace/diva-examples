package com.diva.util

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.diva.models.auth.Session
import io.github.juevigrace.diva.core.models.DivaError
import io.github.juevigrace.diva.core.models.DivaErrorException
import io.github.juevigrace.diva.core.models.DivaResult
import io.github.juevigrace.diva.core.models.Option
import io.github.juevigrace.diva.core.models.fold
import io.github.juevigrace.diva.core.models.getOrNull
import io.github.juevigrace.diva.core.models.isEmpty
import io.github.juevigrace.diva.core.models.toDivaError
import io.github.juevigrace.diva.core.models.tryResult
import io.ktor.server.auth.jwt.JWTCredential
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.util.AttributeKey
import sun.jvm.hotspot.HelloWorld.e
import java.util.UUID
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.toJavaInstant

class JwtHelper(
    private val secret: String,
    private val issuer: String,
    private val audience: String,
) {
    companion object {
        private const val ACCESS_EXPIRES_IN: String = "3600000ms"
        private const val REFRESH_EXPIRES_IN: String = "86400000ms"

        private const val USER_ID_CLAIM_KEY = "user_id"
        private const val SESSION_ID_CLAIM_KEY = "session_id"
    }

    val jwtVerifier: JWTVerifier =
        JWT
            .require(Algorithm.HMAC256(secret))
            .withAudience(audience)
            .withIssuer(issuer)
            .build()

    fun createAccessToken(userId: String, sessionId: String): String = createJwt(userId, sessionId, ACCESS_EXPIRES_IN)

    fun createRefreshToken(userId: String, sessionId: String): String = createJwt(userId, sessionId, REFRESH_EXPIRES_IN)

    @OptIn(ExperimentalTime::class)
    private fun createJwt(
        userId: String,
        sessionId: String,
        expiresIn: String,
    ): String =
        JWT
            .create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim(USER_ID_CLAIM_KEY, userId)
            .withClaim(SESSION_ID_CLAIM_KEY, sessionId)
            .withSubject(userId)
            .withExpiresAt(
                Clock.System
                    .now()
                    .plus(Duration.parse(expiresIn))
                    .toJavaInstant(),
            ).sign(Algorithm.HMAC256(secret))

    suspend fun validate(
        credential: JWTCredential,
        sessionCallBack: suspend (sessionId: UUID) -> DivaResult<Option<Session>, DivaError>
    ): JWTPrincipal? {
        return tryResult(
            onError = { e ->
                println(e.toDivaError("JwtHelper.validate"))
            }
        ) {
            val sessionId: String? = credential.payload.getClaim(SESSION_ID_CLAIM_KEY).asString()
            val userId: String? = credential.payload.getClaim(USER_ID_CLAIM_KEY).asString()

            val principal: JWTPrincipal? = when {
                sessionId == null -> {
                    error("Session id is null")
                }
                userId == null -> {
                    error("User id is null")
                }
                else -> {
                    val parsedId = UUID.fromString(sessionId)
                    sessionCallBack(parsedId).fold(
                        onFailure = { err ->
                            throw DivaErrorException(err)
                        },
                        onSuccess = { option ->
                            if (option.isEmpty()) {
                                return@fold null
                            }
                            JWTPrincipal(credential.payload)
                        }
                    )
                }
            }
            DivaResult.success(principal)
        }.getOrNull()
    }
}
