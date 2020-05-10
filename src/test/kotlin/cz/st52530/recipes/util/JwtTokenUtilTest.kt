package cz.st52530.recipes.util

import cz.st52530.recipes.model.security.UserDetailsModel
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class JwtTokenUtilTest {

    private val underTest: JwtTokenUtil = JwtTokenUtil("test-secret")

    @Test
    fun givenBearerPlusToken_thenTokenValueExtracted() {
        val input = "Bearer tokenValue"
        val result = underTest.extractBareToken(input)
        assertEquals("tokenValue", result)
    }

    @Test
    fun givenInvalidToken_thenTokenStaysTheSame() {
        val input = "xJI"
        val result = underTest.extractBareToken(input)
        assertEquals(input, result)
    }

    @Test
    fun givenValidToken_thenExtractUsername() {
        val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJteVVzZXJuYW1lIiwiZXhwIjoxNTg5NzM4MjI5LCJpYXQiOjE1ODkxMzM0Mjl9.2RDQfFCeuXfKRWRc3ySAZBie9BWLtSDLn9okeMTY3tHlgIX4HG5NuE03bzwHPYzbSaU47dxL7F9MTrc5BH393w"
        val result = underTest.getUsernameFromToken(token)
        assertEquals("myUsername", result)
    }

    @Test
    fun givenDifferentSignatureToken_thenThrowException() {
        val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJteVVzZXJuYW1lIiwiZXhwIjoxNTg5NzM4MjI5LCJpYXQiOjE1ODkxMzM0Mjl9.MbWgZx9wRX7KNTOTHA0DhyX8-MwBhg1UHX9vByroN6BnsyN_Q3wlrZTgWyA6mIPmaA9MHeKP95VRfMKu4NVpQQ"
        assertThrows(SignatureException::class.java) {
            underTest.getUsernameFromToken(token)
        }
    }

    @Test
    fun whenGenerateToken_ThenReturnsValidToken() {
        val details = UserDetailsModel("myUsername", "test")
        val token = underTest.generateToken(details)
        assertTrue(underTest.validateToken(token, details))
    }

    @Test
    fun givenOutdatedToken_thenValidateThrowsException() {
        val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJteVVzZXJuYW1lIiwiZXhwIjowLCJpYXQiOjE1ODkxMzM3NTV9._Oo6_E6nxVVPQPnSUaVIVop57ilE_-VBx4tzgJjBKnS_5ndGFQb3ltxVBc2PIR4LbVf_iMbm6g9S9-88HyhSig"
        assertThrows(ExpiredJwtException::class.java) {
            underTest.validateToken(token, UserDetailsModel("myUsername", "test"))
        }
    }

    @Test
    fun givenWrongUsername_thenValidateReturnsFalse() {
        val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJteVVzZXJuYW1lIiwiZXhwIjo2MTg1NDI3NDgwMCwiaWF0IjoxNTg5MTMzOTEwfQ.K0hEn2Nvxt8dKd6mckQVhh9W95IBaPxUQj_zoliaG7-WOfzxZ-9tFM539Mf_ZdsiOmFfLWijCOCq2BqmlGANBA"
        val result = underTest.validateToken(token, UserDetailsModel("wrong", "test"))
        assertFalse(result)
    }

    @Test
    fun givenValidDetails_thenValidateReturnsTrue() {
        val token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJteVVzZXJuYW1lIiwiZXhwIjo2MTg1NDI3NDgwMCwiaWF0IjoxNTg5MTMzOTEwfQ.K0hEn2Nvxt8dKd6mckQVhh9W95IBaPxUQj_zoliaG7-WOfzxZ-9tFM539Mf_ZdsiOmFfLWijCOCq2BqmlGANBA"
        val result = underTest.validateToken(token, UserDetailsModel("myUsername", "test"))
        assertTrue(result)
    }
}