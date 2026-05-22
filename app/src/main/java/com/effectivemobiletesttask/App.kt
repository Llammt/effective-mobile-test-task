package com.effectivemobiletesttask

import android.app.Application
import com.effectivemobile.testtask.data.di.dataModule
import com.effectivemobile.testtask.data.di.domainModule
import com.effectivemobile.testtask.features.di.featuresModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(listOf(dataModule, domainModule, featuresModule))
        }
    }
}