package com.retail.kbv.retailapp.injections

import com.retail.kbv.retailapp.rxbus.RxBus
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
public class UserModule {
    @UserScope
    @Provides
    @Named("localBus")
    public fun provideLocalBus(): RxBus {
        return RxBus()
    }
}