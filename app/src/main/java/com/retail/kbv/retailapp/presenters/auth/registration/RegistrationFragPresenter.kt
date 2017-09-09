package com.retail.kbv.retailapp.presenters.auth.registration

import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.model.UserCredentials
import com.retail.kbv.retailapp.presenters.BaseNetworkPresenter
import timber.log.Timber
import javax.inject.Inject

class RegistrationFragPresenter(component: UserComponent): BaseNetworkPresenter<RegistrationFragView>() {
    init {
        component.inject(this)
    }

    @Inject
    lateinit var userCredentials: UserCredentials

    private var auth: FirebaseAuth? = null

    private var email = ""
    private var password = ""

    fun setupWatchers(emailField: EditText, passwordField: EditText) {
        disposables?.add(
                RxTextView.afterTextChangeEvents(emailField)
                        .subscribe{event ->
                            email = event.editable().toString()
                        })
        disposables?.add(
                RxTextView.afterTextChangeEvents(passwordField)
                        .subscribe{event ->
                            password = event.editable().toString()
                        }
        )
    }

    fun makeRegistration() {
        auth = FirebaseAuth.getInstance()
        auth?.createUserWithEmailAndPassword(email, password)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth?.currentUser
                        userCredentials.email = user?.email
                    } else {
                        Timber.d(task.exception, "Not success auth")
                    }
                }
    }
}
