package cpp.template.baseproject.security.infrastructure.repository

import cpp.template.baseproject.security.infrastructure.repository.model.RefreshToken
import cpp.template.baseproject.security.infrastructure.repository.model.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


interface RefreshTokenRepository : JpaRepository<RefreshToken, UUID> {
//    fun findById(id: UUID?): Optional<RefreshToken>
    fun findByToken(token: String?): Optional<RefreshToken>
    fun deleteByUser(get: User): Int
}