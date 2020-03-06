package xyz.eviao.platform.security.authentication.email

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService

class EmailAuthenticationProvider(
        val userDetailsService: UserDetailsService
) : AuthenticationProvider {

    override fun authenticate(authentication: Authentication?): Authentication {
        val token = authentication as UsernamePasswordAuthenticationToken
        val email = token.name
        val code = token.credentials

        if (!"123123".equals(code)) {
            throw BadCredentialsException("Invalid authentication email code")
        }

        val userDetails = userDetailsService.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(userDetails.username, userDetails.password, userDetails.authorities).apply {
            details = token.details
        }
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}