package cz.st52530.recipes.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenUtil(
        @Value("\${jwt.secret}")
        private val secret: String
) {
    /**
     * Removes Bearer part of the token.
     */
    fun extractBareToken(token: String): String {
        return if (token.startsWith("Bearer ")) {
            token.substring(7)
        } else {
            token
        }
    }

    /**
     * Retrieve username from jwt token.
     */
    fun getUsernameFromToken(token: String): String {
        return getClaimFromToken(token) { claims: Claims ->
            claims.subject
        }
    }

    /**
     * Retrieve expiration date from jwt token.
     */
    private fun getExpirationDateFromToken(token: String): Date {
        return getClaimFromToken(token) { claims: Claims ->
            claims.expiration
        }
    }

    private fun <T> getClaimFromToken(token: String, claimsResolver: (claims: Claims) -> T): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver(claims)
    }

    /**
     * For retrieving any information from token we will need the secret key.
     */
    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .body
    }

    /**
     * Check if the token has expired.
     */
    private fun isTokenExpired(token: String): Boolean {
        val expiration: Date = getExpirationDateFromToken(token)
        return expiration.before(Date())
    }

    /**
     * Generate token for user.
     */
    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    /**
     * While creating the token -
     * 1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     * 2. Sign the JWT using the HS512 algorithm and secret key.
     * 3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     * compaction of the JWT to a URL-safe string
     */
    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
    }

    /**
     * Validate token.
     */
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    companion object {

        // 7 Days.
        const val JWT_TOKEN_VALIDITY: Long = 7 * 24 * 60 * 60 * 1000
    }
}