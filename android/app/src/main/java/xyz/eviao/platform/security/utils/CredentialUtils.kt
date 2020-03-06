package xyz.eviao.platform.security.utils

import okhttp3.Credentials

object CredentialUtils {

    fun basic(username: String = "test", password: String = "test") = Credentials.basic(username, password)
}