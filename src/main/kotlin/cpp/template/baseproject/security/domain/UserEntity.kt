package cpp.template.baseproject.security.domain

import java.util.*

data class UserEntity(
    val id: UUID,
    val username: String,
    val email: String,
    val password: String,
    val roles: Set<String>
)