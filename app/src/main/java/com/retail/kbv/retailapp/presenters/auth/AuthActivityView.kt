package com.retail.kbv.retailapp.presenters.auth

import com.retail.kbv.retailapp.presenters.NetworkRequestView


interface AuthActivityView: NetworkRequestView {
    fun showLoginForm()
    fun openRegisterForm()
}
