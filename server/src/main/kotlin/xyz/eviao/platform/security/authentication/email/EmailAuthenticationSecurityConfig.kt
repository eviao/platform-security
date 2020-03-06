package xyz.eviao.platform.security.authentication.email

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.stereotype.Component

@Component
class EmailAuthenticationSecurityConfig(
        val passwordEncoder: PasswordEncoder,
        val authenticationSuccessHandler: AuthenticationSuccessHandler,
        val authenticationFailureHandler: AuthenticationFailureHandler,
        val userDetailsService: UserDetailsService
) : SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {

    override fun configure(builder: HttpSecurity) {
        val filter = EmailAuthenticationFilter()
        filter.setAuthenticationManager(builder.getSharedObject(AuthenticationManager::class.java))
        filter.setAuthenticationSuccessHandler(authenticationSuccessHandler)
        filter.setAuthenticationFailureHandler(authenticationFailureHandler)

        val provider = EmailAuthenticationProvider(userDetailsService)
        builder.authenticationProvider(provider).addFilterAfter(filter, UsernamePasswordAuthenticationFilter::class.java)
    }
}