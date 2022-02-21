package cpp.template.baseproject

import cpp.template.baseproject.core.domain.CommandQueryBus
import cpp.template.baseproject.core.infrastructure.cqrs.CommandQueryBusBasic
import cpp.template.baseproject.security.application.DoLoginHandler
import cpp.template.baseproject.security.application.RefreshTokenHandler
import cpp.template.baseproject.security.application.SignUpHandler
import cpp.template.baseproject.security.application.ports.AuthenticationService
import cpp.template.baseproject.security.application.ports.JwtUtils
import cpp.template.baseproject.security.application.ports.UserService
import cpp.template.baseproject.security.infrastructure.adapters.RefreshTokenServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ProjectConfiguration {

    @Bean
    fun commandQueryBusConfiguration(
        jwtUtils: JwtUtils,
        refreshTokenService: RefreshTokenServiceImpl,
        authenticationService: AuthenticationService,
        userService: UserService
    ): CommandQueryBus {
        val bus = CommandQueryBusBasic()
        bus.registerQueryHandler(DoLoginHandler(authenticationService, jwtUtils, refreshTokenService))
        bus.registerCommandHandler(SignUpHandler(userService))
        bus.registerQueryHandler(RefreshTokenHandler(refreshTokenService, userService, jwtUtils))
        return bus
    }
}