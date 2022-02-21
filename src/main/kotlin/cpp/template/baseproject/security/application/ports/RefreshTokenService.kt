package cpp.template.baseproject.security.application.ports

import cpp.template.baseproject.security.application.data.RefreshTokenData
import java.util.*

interface RefreshTokenService {
    fun find(refreshTokenId: String): Optional<RefreshTokenData>

    fun createRefreshToken(userId: UUID): RefreshTokenData

    fun refreshRefreshToken(oldRefreshToken: RefreshTokenData): RefreshTokenData

    fun verifyExpiration(token: RefreshTokenData): RefreshTokenData?
}
