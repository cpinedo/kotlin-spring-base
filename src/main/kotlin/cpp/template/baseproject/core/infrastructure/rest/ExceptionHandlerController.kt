package cpp.template.baseproject.core.infrastructure.rest

import cpp.template.baseproject.core.domain.CoreRestException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandlerController : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [CoreRestException::class])
    protected fun handleSecurityConflict(ex: RuntimeException, request: WebRequest): ResponseEntity<Any> {
        val source: String = ex::class.simpleName ?: "UNKNOWN"
        val exception = ex as CoreRestException
        return handleExceptionInternal(
            ex, ExceptionBody(exception.message, source),
            HttpHeaders(), HttpStatus.valueOf(ex.status.name), request
        )
    }

    data class ExceptionBody(val message: String, val source: String)
}