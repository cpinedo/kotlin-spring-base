package cpp.template.baseproject.security.domain

import cpp.template.baseproject.core.domain.CoreRestException
import cpp.template.baseproject.core.domain.HttpStatus

class BaseSecurityRestException(message:String, status: HttpStatus) : CoreRestException(message, status)