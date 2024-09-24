package org.pointyware.accountability

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 */
@HiltAndroidApp
class AccountabilityApp: Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        } // TODO: plant release tree with crashlytics
    }
}
