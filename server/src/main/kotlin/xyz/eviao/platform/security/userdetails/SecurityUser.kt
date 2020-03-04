package xyz.eviao.platform.security.userdetails

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails


class SecurityUser(
        val uid: Long,

        val name: String,

        private val username: String,

        @field: JsonIgnore
        private val password: String,

        private val enabled: Boolean,

        private val authorities: Collection<GrantedAuthority>
) : UserDetails {

        override fun getUsername(): String = this.username

        override fun getPassword(): String = this.password

        override fun getAuthorities(): Collection<GrantedAuthority> = this.authorities

        override fun isEnabled(): Boolean = this.enabled

        override fun isCredentialsNonExpired(): Boolean = true

        override fun isAccountNonExpired(): Boolean = true

        override fun isAccountNonLocked(): Boolean = true
}