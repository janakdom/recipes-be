package cz.st52530.recipes.security

import cz.st52530.recipes.service.IUserService
import cz.st52530.recipes.util.JwtTokenUtil
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtRequestFilter(
        private val userService: IUserService,
        private val jwtTokenUtil: JwtTokenUtil
) : OncePerRequestFilter() {

    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {
        logger.info("Incoming (${request.method}) request: ${request.requestURI}")

        val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER)
        val (username, token) = parseUsername(authorizationHeader)

        validateTokenAndUpdateContext(request, username, token)

        chain.doFilter(request, response)
    }

    /**
     * @return Pair that contains username and token in this order.
     */
    private fun parseUsername(authorizationHeader: String?): Pair<String?, String?> {
        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token.
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            val token = jwtTokenUtil.extractBareToken(authorizationHeader)
            try {
                return Pair(jwtTokenUtil.getUsernameFromToken(token), token)
            } catch (e: IllegalArgumentException) {
                logger.warn("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                logger.warn("JWT Token has expired")
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String or there was no token header.")
        }

        return Pair(null, null)
    }

    private fun validateTokenAndUpdateContext(
            request: HttpServletRequest,
            username: String?,
            token: String?
    ) {
        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = userService.loadUserByUsername(username)
            // If token is valid configure Spring Security to manually set authentication.
            if (jwtTokenUtil.validateToken(token!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                // After setting the Authentication in the context, we specify that the current user is authenticated.
                // So it passes the Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
    }

    companion object {
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}