package cpp.template.baseproject.core.domain

open class CoreRestException(override val message:String, val status: HttpStatus): RuntimeException()