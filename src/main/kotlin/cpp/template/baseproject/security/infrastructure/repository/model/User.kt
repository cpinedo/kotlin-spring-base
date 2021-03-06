package cpp.template.baseproject.security.infrastructure.repository.model

import cpp.template.baseproject.security.application.data.UserData
import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users", schema = "base")
class User(
    @Id
    @Column
    var id: UUID,

    @Column
    var username: String,

    @Column
    var email: String,

    @Column
    var password: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    @Column
    var roles: MutableSet<Role>
){
    fun toUserData():UserData{
        val roles = this.roles.mapNotNull { it.name }.toSet()
        return UserData(this.id, this.username, this.email, roles)
    }
}