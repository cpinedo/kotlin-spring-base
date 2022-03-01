package cpp.template.baseproject.security.infrastructure.adapters

import cpp.template.baseproject.core.domain.HttpStatus
import cpp.template.baseproject.core.domain.CoreRestException
import cpp.template.baseproject.security.application.data.RefreshTokenData
import cpp.template.baseproject.security.application.ports.RefreshTokenService
import cpp.template.baseproject.security.infrastructure.configuration.SecurityConfigurationValues
import cpp.template.baseproject.security.infrastructure.repository.RefreshTokenRepository
import cpp.template.baseproject.security.infrastructure.repository.UserRepository
import cpp.template.baseproject.security.infrastructure.repository.model.RefreshToken
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RefreshTokenServiceImpl(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,
    private val configurationValues: SecurityConfigurationValues
) : RefreshTokenService {

    override fun find(refreshTokenId: String): Optional<RefreshTokenData> {
        return refreshTokenRepository.findByToken(refreshTokenId).map { it.toRefreshTokenData() }
    }

    override fun createRefreshToken(userId: UUID): RefreshTokenData {
        var refreshToken = RefreshToken(UUID.randomUUID())
        refreshToken.user = userRepository.findById(userId).orElse(null)
        refreshToken.expiryDate = Instant.now().plusMillis(configurationValues.refreshTokenDurationMs)
        refreshToken.token = UUID.randomUUID().toString()
        refreshToken.sessionExpiryDate = Instant.now().plusMillis(configurationValues.sessionDurationMs)
        refreshToken = refreshTokenRepository.save(refreshToken)
        return refreshToken.toRefreshTokenData()
    }

    override fun refreshRefreshToken(oldRefreshToken: RefreshTokenData): RefreshTokenData {
        refreshTokenRepository.findById(oldRefreshToken.id).let {
            val expiryDate = Instant.now().plusMillis(configurationValues.refreshTokenDurationMs)
            if(expiryDate.isBefore(oldRefreshToken.sessionExpiryDate))
                it.get().expiryDate = expiryDate
            else
                it.get().expiryDate = oldRefreshToken.sessionExpiryDate
            it.get().token = UUID.randomUUID().toString()
            return refreshTokenRepository.save(it.get()).toRefreshTokenData()
        }
    }

    override fun verifyExpiration(token: RefreshTokenData): RefreshTokenData? {
        if ((token.expiryDate == null) || (token.expiryDate < Instant.now()) || (token.sessionExpiryDate == null) || (token.sessionExpiryDate < Instant.now())) {
            refreshTokenRepository.findById(token.id).let {
                refreshTokenRepository.delete(it.get())
            }
            throw TokenRefreshException("Refresh token was expired. Please make a new signin request")
        }
        return token
    }

    class TokenRefreshException(message: String) : CoreRestException(message, HttpStatus.UNAUTHORIZED)
}