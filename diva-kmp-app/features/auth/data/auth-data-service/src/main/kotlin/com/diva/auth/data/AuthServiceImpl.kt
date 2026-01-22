package com.diva.auth.data

import com.diva.database.session.SessionStorage
import com.diva.models.api.ApiResponse
import com.diva.models.api.auth.dtos.PasswordUpdateDto
import com.diva.models.api.auth.dtos.SessionDataDto
import com.diva.models.api.auth.dtos.SignInDto
import com.diva.models.api.auth.dtos.SignUpDto
import com.diva.models.api.auth.response.SessionResponse
import com.diva.models.api.user.dtos.CreateUserDto
import com.diva.models.auth.Session
import com.diva.models.session.SessionStatus
import com.diva.models.user.User
import com.diva.util.Encryption
import com.diva.util.JwtHelper
import io.github.juevigrace.diva.core.DivaResult
import io.github.juevigrace.diva.core.errors.DivaError
import io.github.juevigrace.diva.core.errors.asNetworkError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.flatMap
import io.github.juevigrace.diva.core.getOrElse
import io.github.juevigrace.diva.core.map
import io.github.juevigrace.diva.core.mapError
import io.github.juevigrace.diva.core.network.HttpRequestMethod
import io.github.juevigrace.diva.core.network.HttpStatusCodes
import io.github.juevigrace.diva.core.tryResult
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class AuthServiceImpl(
    private val storage: SessionStorage,
    private val jwtHelper: JwtHelper,
) : AuthService {
    override suspend fun passwordReset(
        dto: PasswordUpdateDto,
        onUpdatePassword: suspend (passwordHash: String) -> DivaResult<Unit, DivaError>
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.PATCH, "/api/auth/forgot/password")
            }
        ) {
            val hash: String = Encryption.hashPassword(dto.newPassword)
            onUpdatePassword(hash)
                .mapError { err -> err.asNetworkError(HttpRequestMethod.PATCH, "/api/auth/forgot/password") }
                .map { ApiResponse(message = "Password updated") }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun ping(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/ping")
            }
        ) {
            DivaResult.success(ApiResponse(message = "Pong"))
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun refresh(
        dto: SessionDataDto,
        session: Session
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/refresh")
            }
        ) {
            if (session.device != dto.device || session.userAgent != dto.userAgent) {
                storage.delete(session.id)
                    .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/refresh") }
                return DivaResult.failure(
                    DivaError.NetworkError(
                        method = HttpRequestMethod.POST,
                        url = "/api/auth/refresh",
                        statusCode = HttpStatusCodes.Unauthorized,
                        details = "Invalid session",
                    )
                )
            }

            val updatedSession: Session = createSession(session.id, session.user.id, dto)
                .copy(
                    createdAt = session.createdAt,
                )

            storage
                .update(updatedSession)
                .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/refresh") }
                .map {
                    ApiResponse(
                        data = updatedSession.toResponse(),
                        message = "Session refreshed"
                    )
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun signIn(
        dto: SignInDto,
        onUserSearch: suspend (username: String) -> DivaResult<User, DivaError>
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/signIn")
            }
        ) {
            onUserSearch(dto.username)
                .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/signIn") }
                .flatMap { user ->
                    when {
                        !Encryption.verifyPassword(dto.password, user.passwordHash.getOrElse { "" }) -> {
                            DivaResult.success(
                                ApiResponse(
                                    statusCode = 400,
                                    message = "Invalid credentials"
                                )
                            )
                        }
                        // TODO: this should be placed in the authentication check too
                        !user.userVerified -> {
                            DivaResult.success(
                                ApiResponse(
                                    statusCode = 400,
                                    message = "You are not verified"
                                )
                            )
                        }
                        else -> {
                            val sessionId: Uuid = Uuid.random()
                            val session: Session = createSession(sessionId, user.id, dto.sessionData)
                            storage.insert(session)
                                .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/signIn") }
                                .map {
                                    ApiResponse(
                                        data = session.toResponse(),
                                        message = "Sign in successful"
                                    )
                                }
                        }
                    }
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun signUp(
        dto: SignUpDto,
        onCreateUser: suspend (dto: CreateUserDto) -> DivaResult<Uuid, DivaError>
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/signUp")
            }
        ) {
            onCreateUser(dto.user.copy(password = Encryption.hashPassword(dto.user.password)))
                .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/signUp") }
                .flatMap { userId ->
                    val sessionId: Uuid = Uuid.random()
                    val session: Session = createSession(sessionId, userId, dto.sessionData)
                    storage.insert(session)
                        .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/signUp") }
                        .map {
                            ApiResponse(
                                data = session.toResponse(),
                                message = "Sign up successful"
                            )
                        }
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun signOut(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/signUp")
            }
        ) {
            // TODO: this should just update instead?
            storage
                .updateStatus(sessionId, SessionStatus.CLOSED)
                .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/signUp") }
                .map { ApiResponse(message = "Sign out successful") }
        }
    }

    override suspend fun verifyUser(
        onVerify: suspend () -> DivaResult<Unit, DivaError>,
        onVerified: suspend () -> DivaResult<Unit, DivaError>
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(HttpRequestMethod.POST, "/api/auth/verify/email")
            }
        ) {
            onVerify()
                .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/verify/email") }
                .flatMap {
                    onVerified()
                        .mapError { err -> err.asNetworkError(HttpRequestMethod.POST, "/api/auth/verify/email") }
                        .map { ApiResponse(message = "User verified") }
                }
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    private fun createSession(sessionId: Uuid, userId: Uuid, dto: SessionDataDto): Session {
        val accessToken: String = jwtHelper.createAccessToken(
            userId = userId.toString(),
            sessionId = sessionId.toString()
        )
        val refreshToken: String = jwtHelper.createRefreshToken(
            userId = userId.toString(),
            sessionId = sessionId.toString()
        )

        val now: Instant = Clock.System.now()
        val newSession = Session(
            id = sessionId,
            user = User(id = userId),
            accessToken = accessToken,
            refreshToken = refreshToken,
            device = dto.device,
            status = SessionStatus.ACTIVE,
            // TODO: GET IP
            ipAddress = dto.ipAddress ?: "",
            userAgent = dto.userAgent ?: "",
            expiresAt = now.plus(Duration.parse(SESSION_DURATION_MS)),
            createdAt = now,
            updatedAt = now
        )
        return newSession
    }
    companion object {
        private const val SESSION_DURATION_MS = "86400000ms"
    }
}
