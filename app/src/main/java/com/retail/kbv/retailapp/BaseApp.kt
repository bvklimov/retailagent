package com.retail.kbv.retailapp

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.retail.kbv.retailapp.injections.DaggerUserComponent
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.rxbus.RxBus
import timber.log.Timber

class BaseApp: Application() {
    internal val rxBus = RxBus()
    internal var appComponent: AppComponent? = null
    internal var userComponent: UserComponent? = null

    private fun initComponent() {
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    fun resetActivityComponent() {
        userComponent = null
    }

    fun getUserComponent(): UserComponent {
        if (userComponent == null) {
            userComponent = DaggerUserComponent.builder()
                    .appComponent(appComponent)
                    .build()
        }
        return userComponent!!
    }

    override fun onCreate() {
        super.onCreate()
        initComponent()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())
    }
}
