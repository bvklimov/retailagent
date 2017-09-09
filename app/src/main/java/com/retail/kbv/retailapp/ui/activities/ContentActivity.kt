package com.retail.kbv.retailapp.ui.activities

import android.os.Bundle
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.presenters.content.ContentActivityPresenter
import com.retail.kbv.retailapp.presenters.content.ContentActivityView
import com.retail.kbv.retailapp.rxbus.RxBus
import javax.inject.Inject
import javax.inject.Named

class ContentActivity : FragActivity<ContentActivityView, ContentActivityPresenter>(){

    @field:[Inject Named("globalBus")]
    lateinit var globalRxBus: RxBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        getComponent().inject(this)
        rxBus = globalRxBus
    }

    override fun createPresenter(): ContentActivityPresenter {
        return if (presenter == null) ContentActivityPresenter(getComponent()) else presenter
    }

}
