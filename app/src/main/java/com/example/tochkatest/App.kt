package com.example.tochkatest

import android.app.Application
import android.content.Context

import androidx.multidex.MultiDex

import com.vk.sdk.VKSdk

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        VKSdk.initialize(applicationContext)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
