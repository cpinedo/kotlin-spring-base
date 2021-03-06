package cpp.template.baseproject.security.application.ports

import cpp.template.baseproject.security.domain.JwtToken

interface JwtUtils {
    fun generateTokenFromUsername(username: String): JwtToken
    fun getUserNameFromExpiredJwtToken(token: String): String
    fun getUserNameFromJwtToken(token: String?): String
    fun validateJwtToken(authToken: String?): Boolean
    fun buildToken(username: String, grantedAuthorities: Array<String>): JwtToken
}