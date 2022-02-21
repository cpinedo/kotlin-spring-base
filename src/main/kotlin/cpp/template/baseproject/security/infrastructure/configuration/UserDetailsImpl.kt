package cpp.template.baseproject.security.infrastructure.configuration

import cpp.template.baseproject.security.domain.UserEntity
import cpp.template.baseproject.security.infrastructure.repository.model.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*

data class UserDetailsImpl(val user: User) : UserDetails {

    fun getId(): UUID = user.id

    fun getEmail(): String = user.email

    override fun getAuthorities(): Collection<GrantedAuthority> = user.roles
        .map { role -> SimpleGrantedAuthority(role.role.name) }
        .toList()

    override fun getPassword(): String = user.password

    override fun getUsername(): String = user.username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true

    fun toUserEntity(): UserEntity {
        val roles: Set<String> = this.authorities.map { ga -> ga.authority.toString() }.toSet()
        return UserEntity(this.getId(), username, this.getEmail(), password, roles)
    }

}