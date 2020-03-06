package xyz.eviao.platform.security.service

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import xyz.eviao.platform.security.App

class HttpAuthorizationInterceptor() : Interceptor {

    private fun getToken(): String? {
        val session = App.getContext().getSharedPreferences("session", Context.MODE_PRIVATE)
        return session.getString("access_token", null)
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = getToken()

        val request = if (token.isNullOrBlank()) {
            chain.request()
        } else {
            chain.request().let {
                it.newBuilder()
                    .header("Authorization", token)
                    .method(it.method, it.body)
                    .build()
            }
        }

        return chain.proceed(request)
    }
}