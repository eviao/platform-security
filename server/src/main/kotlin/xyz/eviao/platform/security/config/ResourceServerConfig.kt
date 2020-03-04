package xyz.eviao.platform.security.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

@Configuration
@EnableResourceServer
class ResourceServerConfig(
        val authenticationSuccessHandler: AuthenticationSuccessHandler,
        val authenticationFailureHandler: AuthenticationFailureHandler
) : ResourceServerConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {

        http
                .cors().disable()

                .authorizeRequests()
                .antMatchers("/hello").permitAll()
                .anyRequest().authenticated()

                .and().formLogin()
                .loginProcessingUrl("/auth/login")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)

    }
}