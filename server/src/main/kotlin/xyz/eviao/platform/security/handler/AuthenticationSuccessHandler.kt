package xyz.eviao.platform.security.handler

import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.commons.codec.binary.Base64
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException
import org.springframework.security.oauth2.provider.ClientDetails
import org.springframework.security.oauth2.provider.ClientDetailsService
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.TokenRequest
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Component
class AuthenticationSuccessHandler(
        val objectMapper: ObjectMapper,
        val clientDetailsService: ClientDetailsService,
        val passwordEncoder: PasswordEncoder,
        val authorizationServerTokenServices: AuthorizationServerTokenServices
) : SavedRequestAwareAuthenticationSuccessHandler() {

    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val header = request.getHeader("Authorization")
        if (header == null || !header.startsWith("Basic ")) {
            throw UnapprovedClientAuthenticationException("Invalid authentication client")
        }

        val tokens = extractAndDecodeHeader(header, request)
        val clientId = tokens.first()
        val clientSecret = tokens.last()

        val clientDetails: ClientDetails = clientDetailsService.loadClientByClientId(clientId).apply {
            if (this == null) throw UnapprovedClientAuthenticationException("Invalid authentication client id")
        }
        if (!passwordEncoder.matches(clientSecret, clientDetails.clientSecret)) {
            throw UnapprovedClientAuthenticationException("Invalid authentication client secret")
        }

        val tokenRequest = TokenRequest(emptyMap<String, String>(), clientId, clientDetails.scope, "all")
        val oauth2Request = tokenRequest.createOAuth2Request(clientDetails)
        val oauth2Authentication = OAuth2Authentication(oauth2Request, authentication)
        val token = authorizationServerTokenServices.createAccessToken(oauth2Authentication)

        response.contentType = "application/json;charset=UTF-8"
        response.writer.write(objectMapper.writeValueAsString(token))
    }

    private fun extractAndDecodeHeader(header: String, request: HttpServletRequest): List<String> {
        val base64Token = header.substring(6)
        val decoded = Base64.decodeBase64(base64Token)
        val token = decoded.toString(Charsets.UTF_8)

        val delim = token.indexOf(":").apply {
            if (this == -1) throw BadCredentialsException("Invalid authentication token")
        }
        return listOf(token.substring(0, delim), token.substring(delim + 1))
    }
}