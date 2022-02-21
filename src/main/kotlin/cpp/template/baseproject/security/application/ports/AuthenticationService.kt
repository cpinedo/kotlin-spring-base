package cpp.template.baseproject.security.application.ports

import cpp.template.baseproject.security.domain.UserEntity

interface AuthenticationService {
    fun performAuthentication(user: String, password: String): UserEntity
}