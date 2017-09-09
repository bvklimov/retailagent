package com.retail.kbv.retailapp.presenters.auth

import com.google.firebase.auth.FirebaseAuth
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.model.UserCredentials
import com.retail.kbv.retailapp.presenters.BaseNetworkPresenter
import javax.inject.Inject

class AuthActivityPresenter(userComponent: UserComponent): BaseNetworkPresenter<AuthActivityView>() {
    init {
        userComponent.inject(this)
    }

    @Inject
    lateinit var userCredentials: UserCredentials

    private var auth: FirebaseAuth? = null

    fun checkAuth() {
        auth = FirebaseAuth.getInstance()
        val currentUser = auth?.currentUser
        if (currentUser == null)
            view.openRegisterForm()
        else
            userCredentials.email = currentUser.email
    }
}
