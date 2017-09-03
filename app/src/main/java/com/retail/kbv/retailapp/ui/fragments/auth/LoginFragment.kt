package com.retail.kbv.retailapp.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.presenters.auth.LoginFragPresenter
import com.retail.kbv.retailapp.presenters.auth.LoginFragView
import com.retail.kbv.retailapp.presenters.auth.login.LoginFragViewState
import com.retail.kbv.retailapp.rxbus.RxBus
import com.retail.kbv.retailapp.ui.fragments.BaseFragment

import kotlinx.android.synthetic.main.frag_login.*
import javax.inject.Inject
import javax.inject.Named

class LoginFragment: BaseFragment<LoginFragView, LoginFragPresenter, LoginFragViewState>() {

    @field:[Inject Named("globalBus")]
    lateinit var globalRxBus: RxBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        rxBus = globalRxBus
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_login, container, false)
    }

    override fun onNewViewStateInstance() {
    }

    override fun backPressedAction() {
        getCurrentActiity().hideApp()
    }

    override fun createPresenter(): LoginFragPresenter {
        return if (presenter == null) LoginFragPresenter() else presenter
    }

    override fun createViewState(): LoginFragViewState {
        return if (viewState == null) LoginFragViewState() else viewState
    }

}