package com.retail.kbv.retailapp

import android.content.Context
import com.retail.kbv.retailapp.rxbus.RxBus
import dagger.Component
import javax.inject.Named
import javax.inject.Singleton

@Component(modules = arrayOf(AppModule::class))
@Singleton
interface AppComponent {
    fun context(): Context
    @Named("globalBus")
    fun rxBus(): RxBus
}
