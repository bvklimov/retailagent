package com.retail.kbv.retailapp.presenters

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter

abstract class BaseNetworkPresenter<V: NetworkRequestView>: MvpNullObjectBasePresenter<V>() {

}