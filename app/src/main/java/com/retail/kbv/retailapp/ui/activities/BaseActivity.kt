package com.retail.kbv.retailapp.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import com.hannesdorfmann.mosby3.mvp.MvpActivity
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.retail.kbv.retailapp.BaseApp
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.rxbus.RxBus
import com.retail.kbv.retailapp.rxbus.events.BackPressedEvent
import java.lang.NullPointerException

abstract class BaseActivity<V: MvpView, P: MvpPresenter<V>>: MvpActivity<V, P>() {
    var userComponent: UserComponent? = null

    lateinit var app: BaseApp
    lateinit var rxBus: RxBus


    fun changeActivity(cls: Class<out Activity>) {
        changeActivity(cls, -1)
    }

    fun changeActivity(cls: Class<out Activity>, flags: Int) {
        intent = Intent(this, cls)
        if (flags != -1) intent.flags = flags
        startActivity(intent)
    }

    fun changeActivity(cls: Class<out Activity>, extras: Bundle?, flags: Int) {
        intent = Intent(this, cls)
        if (extras != null) intent.putExtras(extras)
        if (flags != -1) intent.flags = flags
        startActivity(intent)
    }

    fun changeActivity(cls: Class<out Activity>, extras: Bundle?, flags: Int, reset: Boolean) {
        if (reset) resetComponent()
        changeActivity(cls, extras, flags)
    }

    fun changeActivityForResult(cls: Class<out Activity>, extras: Bundle?, flags: Int, requestCode: Int) {
        intent = Intent(this, cls)
        if (extras != null) intent.putExtras(extras)
        if (flags != -1) intent.flags = flags
        startActivityForResult(intent, requestCode)
    }

    protected fun getComponent(): UserComponent? {
        if (userComponent == null)
            addComponent()
        return userComponent
    }

    private fun addComponent() {
        userComponent = app.userComponent
    }

    protected fun resetComponent() {
        app.resetActivityComponent()
    }

    fun backAction() {
        hideApp();
    }

    fun hideApp() {
        moveTaskToBack(true)
    }

    fun hideKeyboard() {
        if (isFinishing) return
        try {
            val inputManager: InputMethodManager = baseContext
                    .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val focus = currentFocus ?: return
            inputManager.hideSoftInputFromWindow(focus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (rxBus.hasObservers()) rxBus.send(BackPressedEvent())
        else hideApp()
    }
}