package cpp.template.baseproject.example.infrastructure.resource

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
internal class TestController {
    @GetMapping("/public")
    fun publicContent(): ResponseEntity<*> = ResponseEntity.ok<Any>(MessageResponse("Public"))

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    fun userContent(): ResponseEntity<*> = ResponseEntity.ok<Any>(MessageResponse("User only"))

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    fun adminContent(): ResponseEntity<*> = ResponseEntity.ok<Any>(MessageResponse("Admin only"))

    @GetMapping("/both")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    fun bothContent(): ResponseEntity<*> = ResponseEntity.ok<Any>(MessageResponse("Admin or User"))

    data class MessageResponse(val message: String)
}
