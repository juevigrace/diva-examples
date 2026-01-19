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
import io.github.juevigrace.diva.core.errors.DivaErrorException
import io.github.juevigrace.diva.core.errors.asNetworkError
import io.github.juevigrace.diva.core.errors.toDivaError
import io.github.juevigrace.diva.core.fold
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
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.PATCH,
                    "/api/auth/forgot/password",
                    HttpStatusCodes.InternalServerError
                )
            }
        ) {
            val hash = Encryption.hashPassword(dto.newPassword)
            onUpdatePassword(hash)
                .mapError { err -> throw DivaErrorException(err) }
                .map { _ -> ApiResponse(message = "Password updated") }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun ping(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/ping",
                    HttpStatusCodes.InternalServerError
                )
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
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/refresh",
                    HttpStatusCodes.InternalServerError
                )
            }
        ) {
            val newAccessToken = jwtHelper.createAccessToken(
                userId = session.user.id.toString(),
                sessionId = session.id.toString()
            )
            val newRefreshToken = jwtHelper.createRefreshToken(
                userId = session.user.id.toString(),
                sessionId = session.id.toString()
            )

            // TODO: make checks with the received session data
            val updatedSession: Session = session.copy(
                accessToken = newAccessToken,
                refreshToken = newRefreshToken,
                status = SessionStatus.ACTIVE,
                device = dto.device,
                ipAddress = dto.ipAddress ?: "",
                userAgent = dto.userAgent ?: "",
                updatedAt = Clock.System.now()
            )

            storage
                .update(updatedSession)
                .mapError { err -> throw DivaErrorException(err) }
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
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/refresh",
                    HttpStatusCodes.InternalServerError
                )
            }
        ) {
            onUserSearch(dto.username).fold(
                onFailure = { err -> throw DivaErrorException(err) },
                onSuccess = { user ->
                    when {
                        !Encryption.verifyPassword(dto.password, user.passwordHash.getOrElse { "" }) -> {
                            DivaResult.success(
                                ApiResponse(
                                    statusCode = 400,
                                    message = "Invalid credentials"
                                )
                            )
                        }
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
                            val accessToken: String = jwtHelper.createAccessToken(
                                userId = user.id.toString(),
                                sessionId = sessionId.toString()
                            )
                            val refreshToken: String = jwtHelper.createRefreshToken(
                                userId = user.id.toString(),
                                sessionId = sessionId.toString()
                            )

                            val now: Instant = Clock.System.now()
                            val newSession = Session(
                                id = sessionId,
                                user = user,
                                accessToken = accessToken,
                                refreshToken = refreshToken,
                                device = dto.sessionData.device,
                                status = SessionStatus.ACTIVE,
                                // TODO: GET IP
                                ipAddress = dto.sessionData.ipAddress ?: "",
                                userAgent = dto.sessionData.userAgent ?: "",
                                expiresAt = now.plus(Duration.parse("86400000ms")), // 24 hours
                                createdAt = now,
                                updatedAt = now
                            )

                            storage.insert(newSession)
                                .mapError { err -> throw DivaErrorException(err) }
                                .map {
                                    ApiResponse(
                                        data = newSession.toResponse(),
                                        message = "Sign in successful"
                                    )
                                }
                        }
                    }
                }
            )
        }
    }

    @OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
    override suspend fun signUp(
        dto: SignUpDto,
        onCreateUser: suspend (dto: CreateUserDto) -> DivaResult<Uuid, DivaError>
    ): DivaResult<ApiResponse<SessionResponse>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/signUp",
                    HttpStatusCodes.InternalServerError
                )
            }
        ) {
            onCreateUser(dto.user.copy(password = Encryption.hashPassword(dto.user.password)))
                .fold(
                    onFailure = { err -> throw DivaErrorException(err) },
                    onSuccess = { userId ->
                        val sessionId: Uuid = Uuid.random()
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
                            device = dto.sessionData.device,
                            status = SessionStatus.ACTIVE,
                            // TODO: GET IP
                            ipAddress = dto.sessionData.ipAddress ?: "",
                            userAgent = dto.sessionData.userAgent ?: "",
                            expiresAt = now.plus(Duration.parse("86400000ms")), // 24 hours
                            createdAt = now,
                            updatedAt = now
                        )

                        storage.insert(newSession)
                            .mapError { err -> throw DivaErrorException(err) }
                            .map {
                                ApiResponse(
                                    data = newSession.toResponse(),
                                    message = "Sign up successful"
                                )
                            }
                    }
                )
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun signOut(sessionId: Uuid): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/signUp",
                    HttpStatusCodes.InternalServerError
                )
            }
        ) {
            // TODO: this should just update instead?
            storage
                .delete(sessionId)
                .mapError { err -> throw DivaErrorException(err) }
                .map { _ -> ApiResponse<Nothing>(message = "Sign out successful") }
        }
    }

    override suspend fun verifyUser(
        onVerify: suspend () -> DivaResult<Unit, DivaError>,
        onVerified: suspend () -> DivaResult<Unit, DivaError>
    ): DivaResult<ApiResponse<Nothing>, DivaError.NetworkError> {
        return tryResult(
            onError = { e ->
                e.toDivaError().asNetworkError(
                    HttpRequestMethod.POST,
                    "/api/auth/signUp",
                    HttpStatusCodes.InternalServerError
                )
            }
        ) {
            onVerify()
                .mapError { err -> throw DivaErrorException(err) }
                .map {
                    onVerified().fold(
                        onFailure = { err -> throw DivaErrorException(err) },
                        onSuccess = { ApiResponse(message = "User verified") }
                    )
                }
        }
    }
}
