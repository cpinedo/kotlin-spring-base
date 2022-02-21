package cpp.template.baseproject.security.application.ports

import cpp.template.baseproject.security.application.data.UserData
import cpp.template.baseproject.security.infrastructure.repository.model.Role
import java.util.*
import kotlin.collections.HashSet

interface UserService {
    fun findUserById(id: UUID): UserData
    fun userNameInUse(username: String): Boolean
    fun emailInUse(email: String): Boolean
    fun save(user: UserData, password: String):UserData
}