package adapter.outbound.security

import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import port.outbound.TokenProviderOutboundPort
import java.security.Key
import java.util.*

@Component
class JwtTokenProviderOutboundAdapter(
    @Value("\${jwt.secret:mySecretKeyForJWTTokenGenerationThatShouldBeLongEnough}")
    private val secretKey: String,
    @Value("\${jwt.expiration:86400000}") // 24시간
    private val expirationTime: Long
) : TokenProviderOutboundPort {

    private val key: Key by lazy {
        Keys.hmacShaKeyFor(secretKey.toByteArray())
    }

    override fun generateToken(username: String, role: String): String {
        val now = Date()
        val expiryDate = Date(now.time + expirationTime)

        return Jwts.builder()
            .setSubject(username)
            .claim("role", role)
            .setIssuedAt(now)
            .setExpiration(expiryDate)
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
    }

    override fun validateToken(token: String): Boolean {
        return try {
            Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
            true
        } catch (ex: JwtException) {
            false
        } catch (ex: IllegalArgumentException) {
            false
        }
    }

    override fun getUsername(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims.subject
    }

    override fun getRole(token: String): String {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        return claims["role"] as String
    }
}