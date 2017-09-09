package com.retail.kbv.retailapp.ui.fragments.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.presenters.auth.registration.RegistrationFragViewState
import com.retail.kbv.retailapp.presenters.auth.registration.RegistrationFragPresenter
import com.retail.kbv.retailapp.presenters.auth.registration.RegistrationFragView
import com.retail.kbv.retailapp.rxbus.RxBus
import com.retail.kbv.retailapp.ui.fragments.BaseFragment
import kotlinx.android.synthetic.main.frag_registration.*
import javax.inject.Inject
import javax.inject.Named

class RegistrationFragment: BaseFragment<RegistrationFragView, RegistrationFragPresenter,
        RegistrationFragViewState>(), RegistrationFragView {

    @field:[Inject Named("globalBus")]
    lateinit var globalRxBus: RxBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        rxBus = globalRxBus
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_registration, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setupWatchers(fragReg_email, fragReg_password)
        frgaReg_button.setOnClickListener { presenter.makeRegistration() }
    }

    override fun onNewViewStateInstance() {
    }

    override fun backPressedAction() {
        getCurrentActiity().hideApp()
    }

    override fun createPresenter(): RegistrationFragPresenter {
        return if (presenter == null) RegistrationFragPresenter(appComponent) else presenter
    }

    override fun createViewState(): RegistrationFragViewState {
        return if (viewState == null) RegistrationFragViewState() else viewState
    }

}