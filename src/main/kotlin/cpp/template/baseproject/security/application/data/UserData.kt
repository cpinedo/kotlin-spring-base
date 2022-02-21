package cpp.template.baseproject.security.application.data

import java.util.UUID

class UserData (val id: UUID, val username:String, val email:String, val roles:Set<String>)
