package xyz.eviao.platform.security

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        private lateinit var context: Context
        fun getContext() = context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}