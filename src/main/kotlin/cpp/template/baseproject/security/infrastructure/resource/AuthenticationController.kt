package cpp.template.baseproject.security.infrastructure.resource

import cpp.template.baseproject.core.domain.CommandQueryBus
import cpp.template.baseproject.core.domain.CoreRestException
import cpp.template.baseproject.core.domain.HttpStatus
import cpp.template.baseproject.security.application.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/auth")
class AuthenticationController(
    @Autowired
    val commandQueryBus: CommandQueryBus
) {
    @PostMapping("/login")
    fun authenticateUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<JwtResponse> {
        val result: LoginResponse = commandQueryBus.dispatch(LoginQuery(loginRequest.username, loginRequest.password))
        return ResponseEntity.ok(JwtResponse.Builders.of(result))
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody signUpRequest: SignupRequest): ResponseEntity<MessageResponse> {
        @Suppress("IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")
        commandQueryBus.dispatch(
            SignUpCommand(
                signUpRequest.username,
                signUpRequest.password,
                signUpRequest.email,
                signUpRequest.roles
            )
        )
        return ResponseEntity.ok(MessageResponse("User registered successfully!"))
    }

    @PostMapping("/refreshtoken")
    fun refreshtoken(
        @RequestBody request: TokenRefreshRequest,
        @RequestHeader("Authorization") authorizationHeader: String
    ): ResponseEntity<JwtResponse> {
        val jwtToken: String =
            extractTokenFromAuthorizationHeader(authorizationHeader) ?: throw JwtTokenNotPresentException()
        val result: RefreshTokenResponse = commandQueryBus.dispatch(RefreshTokenQuery(request.refreshToken, jwtToken))
        return ResponseEntity.ok(JwtResponse.Builders.of(result))
    }

    class JwtTokenNotPresentException : CoreRestException("Missing jwt token", HttpStatus.BAD_REQUEST)

    private fun extractTokenFromAuthorizationHeader(header: String): String? {
        if (!StringUtils.hasText(header) || !header.startsWith("Bearer ")) {
            return null;
        }
        return header.substring(7, header.length)
    }

    data class TokenRefreshRequest(val refreshToken: String)

    data class SignupRequest(val username: String, val password: String, val email: String, val roles: Set<String>)

    data class MessageResponse(val message: String)

    data class JwtResponse(
        val token: String,
        val refreshToken: String,
        val tokenExpTime: Long,
        val refreshTokenExpTime: Long
    ) {
        val type: String = "Bearer"

        object Builders {
            fun of(loginResponse: LoginResponse): JwtResponse {
                return JwtResponse(
                    loginResponse.token.token,
                    loginResponse.refreshToken.token!!,
                    loginResponse.token.expirationTime,
                    loginResponse.refreshToken.expiryDate!!.toEpochMilli()
                )
            }

            fun of(loginResponse: RefreshTokenResponse): JwtResponse {
                return JwtResponse(
                    loginResponse.token.token,
                    loginResponse.refreshToken.token!!,
                    loginResponse.token.expirationTime,
                    loginResponse.refreshToken.expiryDate!!.toEpochMilli()
                )
            }
        }
    }

    data class LoginRequest(val username: String, val password: String)

}