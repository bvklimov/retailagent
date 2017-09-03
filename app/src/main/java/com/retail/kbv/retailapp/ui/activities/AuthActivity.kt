package com.retail.kbv.retailapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.rxbus.RxBus
import javax.inject.Inject
import javax.inject.Named

class AuthActivity : AppCompatActivity() {

    @Inject
    @Named("globalBus")
    lateinit var globalRxBus: RxBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }
}
