package cpp.template.baseproject.security.infrastructure.adapters

import cpp.template.baseproject.security.application.ports.AuthenticationService
import cpp.template.baseproject.security.domain.UserEntity
import cpp.template.baseproject.security.infrastructure.configuration.UserDetailsImpl
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl(
    private val authenticationManager: AuthenticationManager
) : AuthenticationService {
    override fun performAuthentication(user: String, password: String): UserEntity {
        val authentication: Authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(user, password)
        )
        SecurityContextHolder.getContext().authentication = authentication

        return (authentication.principal as UserDetailsImpl).toUserEntity()
    }
}