package com.retail.kbv.retailapp.ui.activities

import android.os.Bundle
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.presenters.auth.AuthActivityPresenter
import com.retail.kbv.retailapp.presenters.auth.AuthActivityView
import com.retail.kbv.retailapp.rxbus.RxBus
import com.retail.kbv.retailapp.ui.fragments.auth.LoginFragment
import javax.inject.Inject
import javax.inject.Named

import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : FragActivity<AuthActivityView, AuthActivityPresenter>(), AuthActivityView {

    @field:[Inject Named("globalBus")]
    lateinit var globalRxBus: RxBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        getComponent().inject(this)
        rxBus = globalRxBus
        showLoginForm()
    }

    override fun createPresenter(): AuthActivityPresenter {
        return if (presenter == null) AuthActivityPresenter() else presenter
    }

    override fun showLoginForm() {
        switchToFragment(LoginFragment::class, act_mainContainer.id)
    }

}
