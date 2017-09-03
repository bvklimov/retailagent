package com.retail.kbv.retailapp.ui.fragments

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateFragment
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState
import com.retail.kbv.retailapp.BaseApp
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.rxbus.RxBus
import com.retail.kbv.retailapp.rxbus.events.BackPressedEvent
import com.retail.kbv.retailapp.rxbus.events.RxBusEvent
import com.retail.kbv.retailapp.ui.activities.BaseActivity
import com.retail.kbv.retailapp.ui.activities.FragActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class BaseFragment<V: MvpView, P: MvpNullObjectBasePresenter<V>, VS: RestorableViewState<V>>
    : MvpViewStateFragment<V, P, VS>() {

    lateinit var appComponent: UserComponent
    private lateinit var rxBus: RxBus
    private var subscription: Disposable? = null

    protected var isLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent = (context.applicationContext as BaseApp).userComponent!!
    }

    protected fun getCurrentActiity(): FragActivity<V,P> {
        return activity as FragActivity<V, P>
    }

    protected abstract fun backPressedAction()

    override fun onResume() {
        super.onResume()
        subscription = rxBus.toObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::localBusAction)
    }

    override fun onPause() {
        super.onPause()
        subscription?.dispose()
        subscription = null
        hideKeyboard()
    }

    private fun localBusAction(rxBusEvent: RxBusEvent) {
        if (rxBusEvent is BackPressedEvent)
            backPressedAction()
    }

    protected fun hideKeyboard() {
        (activity as BaseActivity<*, *>).hideKeyboard()
    }
}