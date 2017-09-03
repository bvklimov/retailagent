package com.retail.kbv.retailapp.injections

import com.retail.kbv.retailapp.AppComponent
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(UserModule::class))
@UserScope
public interface UserComponent {

}