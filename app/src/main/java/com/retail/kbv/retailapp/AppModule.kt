package com.retail.kbv.retailapp

import android.content.Context
import android.support.annotation.NonNull
import com.retail.kbv.retailapp.rxbus.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
public class AppModule(private val app: BaseApp) {

    @Provides
    @Singleton
    public fun provideContext(): Context {
        return app.baseContext
    }

    @Singleton
    @Provides
    @Named("globalBus")
    fun provideRxBus(): RxBus {
        return app.rxBus
    }
}
