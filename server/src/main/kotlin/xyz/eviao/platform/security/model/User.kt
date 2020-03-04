package xyz.eviao.platform.security.model

import com.fasterxml.jackson.annotation.JsonIgnore

data class User(
        val id: Long? = null,

        val name: String? = null,

        val username: String? = null,

        @field: JsonIgnore
        val password: String? = null,

        val email: String? = null,

        val enabled: Boolean = true,

        val authorities: List<String> = emptyList()
)