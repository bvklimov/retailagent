package com.retail.kbv.retailapp.injections

import com.retail.kbv.retailapp.AppComponent
import com.retail.kbv.retailapp.ui.activities.AuthActivity
import com.retail.kbv.retailapp.ui.fragments.auth.LoginFragment
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(UserModule::class))
@UserScope interface UserComponent {
    fun inject(authActivity: AuthActivity)
    fun inject(authActivity: LoginFragment)

}