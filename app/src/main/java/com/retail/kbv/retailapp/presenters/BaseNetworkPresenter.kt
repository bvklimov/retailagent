package com.retail.kbv.retailapp.presenters

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import io.reactivex.disposables.CompositeDisposable

abstract class BaseNetworkPresenter<V: NetworkRequestView>: MvpNullObjectBasePresenter<V>() {
    protected var disposables: CompositeDisposable? = null

    override fun attachView(view: V) {
        super.attachView(view)
        disposables = CompositeDisposable()
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        if (disposables!= null) {
            disposables!!.dispose()
            disposables = null
        }
    }
}