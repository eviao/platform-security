package xyz.eviao.platform.security.service

import org.springframework.core.env.Environment
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import xyz.eviao.platform.security.model.User
import javax.annotation.PostConstruct

@Service
class UserService(
        passwordEncoder: PasswordEncoder
) {

    private val users = listOf<User>(
            User(id = 1L, name = "管理员", password = passwordEncoder.encode("root"), email = "root@eviao.xyz", phone = "111111", enabled = true, authorities = listOf("ADMIN")),
            User(id = 2L, name = "普通用户", password = passwordEncoder.encode("guest"), email = "guest@eviao.xyz", phone = "222222", enabled = true, authorities = listOf("NORMAL"))
    )

    fun findByUsername(username: String): User? {
        return this.users
                .filter { username.equals(it.email) || username.equals(it.phone) }
                .firstOrNull()
    }
}