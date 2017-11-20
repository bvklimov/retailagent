package com.retail.kbv.retailapp.injections

import com.retail.kbv.retailapp.AppComponent
import com.retail.kbv.retailapp.presenters.auth.AuthActivityPresenter
import com.retail.kbv.retailapp.presenters.auth.registration.RegistrationFragPresenter
import com.retail.kbv.retailapp.presenters.content.ContentActivityPresenter
import com.retail.kbv.retailapp.presenters.content.image.ImageFragPresenter
import com.retail.kbv.retailapp.ui.activities.AuthActivity
import com.retail.kbv.retailapp.ui.activities.ContentActivity
import com.retail.kbv.retailapp.ui.fragments.auth.LoginFragment
import com.retail.kbv.retailapp.ui.fragments.auth.RegistrationFragment
import com.retail.kbv.retailapp.ui.fragments.content.BindingFragment
import com.retail.kbv.retailapp.ui.fragments.content.ImageFragment
import dagger.Component

@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(UserModule::class))
@UserScope interface UserComponent {

    fun inject(authActivity: AuthActivity)
    fun inject(contentActivity: ContentActivity)

    fun inject(authActivity: LoginFragment)
    fun inject(registrationFragment: RegistrationFragment)
    fun inject(imageFragment: ImageFragment)
    fun inject(bindingFragment: BindingFragment)

    fun inject(registrationFragPresenter: RegistrationFragPresenter)
    fun inject(authActivityPresenter: AuthActivityPresenter)
    fun inject(contentActivityPresenter: ContentActivityPresenter)
    fun inject(imageFragPresenter: ImageFragPresenter)
}