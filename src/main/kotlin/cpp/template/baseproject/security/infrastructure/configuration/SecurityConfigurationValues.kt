package cpp.template.baseproject.security.infrastructure.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class SecurityConfigurationValues {
    @Value("\${baseproject.jwtRefreshExpirationMs}")
    val refreshTokenDurationMs: Long = 0

    @Value("\${baseproject.jwtSecret}")
    val jwtRawSecret: String? = null

    @Value("\${baseproject.jwtIssuerName}")
    val jwtIssuer: String? = null

    @Value("\${baseproject.jwtExpirationMs}")
    val jwtExpirationMs: Long = 0

}