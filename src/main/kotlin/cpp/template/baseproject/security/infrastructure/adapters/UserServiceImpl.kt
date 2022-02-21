package cpp.template.baseproject.security.infrastructure.adapters

import cpp.template.baseproject.security.application.SignUpHandler
import cpp.template.baseproject.security.application.data.UserData
import cpp.template.baseproject.security.application.ports.UserService
import cpp.template.baseproject.security.infrastructure.repository.RoleRepository
import cpp.template.baseproject.security.infrastructure.repository.UserRepository
import cpp.template.baseproject.security.infrastructure.repository.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val encoder: PasswordEncoder
) : UserService {
    override fun findUserById(id: UUID): UserData {
        return userRepository.findById(id).let { it.get().toUserData() }
    }

    override fun userNameInUse(username: String): Boolean {
        return userRepository.existsByUsername(username)
    }

    override fun emailInUse(email: String): Boolean {
        return userRepository.existsByEmail(email)
    }

    override fun save(user: UserData, password: String):UserData {
        val roles = user.roles.map { roleName: String ->
            roleRepository.findByName(roleName) ?: throw SignUpHandler.RoleDoesNotExistException(roleName)
        }.toHashSet()

        val userEntity = User(
            user.id,
            user.username,
            user.email,
            encoder.encode(password),
            roles
        )

        return userRepository.save(userEntity).toUserData()
    }
}