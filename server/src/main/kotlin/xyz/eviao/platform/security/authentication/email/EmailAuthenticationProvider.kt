package xyz.eviao.platform.security.authentication.email

import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder

class EmailAuthenticationProvider(
        val passwordEncoder: PasswordEncoder,
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
        val result = UsernamePasswordAuthenticationToken(userDetails.username, userDetails.password, userDetails.authorities)
        result.details = token.details
        return result
    }

    override fun supports(authentication: Class<*>?): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}