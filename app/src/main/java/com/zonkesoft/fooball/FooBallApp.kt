package com.zonkesoft.fooball

import android.app.Application
import com.zonkesoft.fooball.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class FooBallApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@FooBallApp)
            modules(appModules)
        }
    }
}