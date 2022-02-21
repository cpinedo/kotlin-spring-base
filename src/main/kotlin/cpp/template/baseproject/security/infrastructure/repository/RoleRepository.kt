package cpp.template.baseproject.security.infrastructure.repository

import cpp.template.baseproject.security.infrastructure.repository.model.Role
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface RoleRepository : JpaRepository<Role, UUID> {
    fun findByName(name: String): Role?
}