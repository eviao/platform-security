package xyz.eviao.platform.security.authentication.email

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class EmailAuthenticationFilter : AbstractAuthenticationProcessingFilter(
        AntPathRequestMatcher("/auth/email", "POST")
) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val email = request.getParameter("email")
        val code = request.getParameter("code")

        val token = UsernamePasswordAuthenticationToken(email, code)
        token.details = authenticationDetailsSource.buildDetails(request)
        return this.authenticationManager.authenticate(token)
    }
}