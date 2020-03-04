package xyz.eviao.platform.security.userdetails

import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import xyz.eviao.platform.security.service.UserService

@Component
class UserDetailsServiceImpl(
        val userService: UserService
) : UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) {
            throw BadCredentialsException("Username cannot be empty")
        }

        val user = userService.findByUsername(username).let {
            if (it == null) throw BadCredentialsException("Your username or password is incorrect. please try again.") else it
        }
        val grantedAuthorities = user.authorities.map { SimpleGrantedAuthority(it) }

        return SecurityUser(
                uid = user.id!!,
                username = user.username!!,
                name = user.name!!,
                password = user.password!!,
                enabled = user.enabled,
                authorities = grantedAuthorities
        )
    }
}