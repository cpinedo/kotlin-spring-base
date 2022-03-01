package cpp.template.baseproject.security.application

import cpp.template.baseproject.core.domain.Handler
import cpp.template.baseproject.core.domain.HttpStatus
import cpp.template.baseproject.security.application.data.RefreshTokenData
import cpp.template.baseproject.security.application.ports.JwtUtils
import cpp.template.baseproject.security.application.ports.RefreshTokenService
import cpp.template.baseproject.security.application.ports.UserService
import cpp.template.baseproject.security.domain.BaseSecurityRestException
import cpp.template.baseproject.security.domain.JwtToken

data class RefreshTokenResponse(val token: JwtToken, val refreshToken: RefreshTokenData)

class RefreshTokenHandler(
    private val refreshTokenService: RefreshTokenService,
    private val userService: UserService,
    private val jwtUtils: JwtUtils
) : Handler<RefreshTokenResponse, RefreshTokenQuery> {
    override fun invoke(request: RefreshTokenQuery): RefreshTokenResponse {
        return refreshTokenService.find(request.refreshToken)
            .filter { refreshToken ->
                val user = refreshToken.userId?.let { userService.findUserById(it) }
                user?.username?.equals(jwtUtils.getUserNameFromExpiredJwtToken(request.token)) ?: false
            }
            .map(refreshTokenService::verifyExpiration)
            .map { oldRefreshToken ->
                val user = oldRefreshToken?.userId?.let { userService.findUserById(it) }
                val jwt: JwtToken = jwtUtils.generateTokenFromUsername(user!!.username)
                val refreshToken: RefreshTokenData = refreshTokenService.refreshRefreshToken(oldRefreshToken)

                RefreshTokenResponse(jwt, refreshToken)
            }
            .orElseThrow {
                BaseSecurityRestException("Refresh token not found!", HttpStatus.UNAUTHORIZED)
            }
    }

}