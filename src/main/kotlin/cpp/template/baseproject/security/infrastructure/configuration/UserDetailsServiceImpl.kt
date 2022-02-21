package cpp.template.baseproject.security.infrastructure.configuration

import cpp.template.baseproject.security.infrastructure.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service


@Service
class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val userEntity = userRepository.findByUsername(username) ?: throw UsernameNotFoundException()
        return UserDetailsImpl(userEntity)
    }

    class UsernameNotFoundException() : RuntimeException()
}
