package com.nerodev.optimaltic

import android.app.Application
import com.nerodev.optimaltic.core.module.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class OptimalTicApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@OptimalTicApp)
            modules(appModule)
        }
    }
}