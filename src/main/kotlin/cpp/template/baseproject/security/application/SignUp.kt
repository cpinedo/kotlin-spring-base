package cpp.template.baseproject.security.application

import cpp.template.baseproject.core.domain.CoreRestException
import cpp.template.baseproject.core.domain.Handler
import cpp.template.baseproject.core.domain.HttpStatus
import cpp.template.baseproject.security.application.data.UserData
import cpp.template.baseproject.security.application.ports.UserService
import java.util.*

class SignUpHandler(
    private val userService: UserService
) : Handler<Unit, SignUpCommand> {
    override fun invoke(request: SignUpCommand) {
        if (userService.userNameInUse(request.username))
            throw FieldAlreadyInUseException("user")

        if (userService.emailInUse(request.email))
            throw FieldAlreadyInUseException("mail")

        val strRoles: Set<String> = request.roles

        val user = UserData(
            UUID.randomUUID(),
            request.username,
            request.email,
            strRoles
        )

        userService.save(user, request.password)
    }

    class FieldAlreadyInUseException(field: String) : CoreRestException("$field already in use!", HttpStatus.CONFLICT)
    class RoleDoesNotExistException(roleName: String) :
        CoreRestException("$roleName role doesn't exist!", HttpStatus.BAD_REQUEST)
}