package cpp.template.baseproject.security.application

import cpp.template.baseproject.core.domain.Handler
import cpp.template.baseproject.security.application.data.RefreshTokenData
import cpp.template.baseproject.security.application.ports.AuthenticationService
import cpp.template.baseproject.security.application.ports.JwtUtils
import cpp.template.baseproject.security.application.ports.RefreshTokenService
import cpp.template.baseproject.security.domain.JwtToken
import cpp.template.baseproject.security.domain.UserEntity

data class LoginResponse(val token: JwtToken, val refreshToken: RefreshTokenData)

class DoLoginHandler(
    private val authenticationService: AuthenticationService,
    private val jwtUtils: JwtUtils,
    private val refreshTokenService: RefreshTokenService
) : Handler<LoginResponse, LoginQuery> {
    override fun invoke(request: LoginQuery): LoginResponse {
        val user: UserEntity = authenticationService.performAuthentication(
            request.user,
            request.password
        )
        val jwt: JwtToken = jwtUtils.generateTokenFromUsername(user.username)
        val refreshToken: RefreshTokenData = refreshTokenService.createRefreshToken(user.id)

        return LoginResponse(jwt, refreshToken)
    }
}


