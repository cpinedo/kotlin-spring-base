package cpp.template.baseproject.security.application

import cpp.template.baseproject.core.domain.Command
import cpp.template.baseproject.core.domain.Query

data class RefreshTokenQuery(val refreshToken: String, val token: String) : Query<RefreshTokenResponse>
data class SignUpCommand(val username: String, val password: String, val email: String, val roles: Set<String>) : Command<Unit>
data class LoginQuery(val user: String, val password: String) : Query<LoginResponse>