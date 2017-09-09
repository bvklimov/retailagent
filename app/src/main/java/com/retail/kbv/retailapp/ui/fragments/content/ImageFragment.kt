package com.retail.kbv.retailapp.ui.fragments.content

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.presenters.content.image.ImageFragPresenter
import com.retail.kbv.retailapp.presenters.content.image.ImageFragView
import com.retail.kbv.retailapp.presenters.content.image.ImageFragViewState
import com.retail.kbv.retailapp.rxbus.RxBus
import com.retail.kbv.retailapp.ui.fragments.BaseFragment
import javax.inject.Inject
import javax.inject.Named

class ImageFragment: BaseFragment<ImageFragView, ImageFragPresenter, ImageFragViewState>(), ImageFragView {

    @field:[Inject Named("globalBus")]
    lateinit var globalBus: RxBus

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        rxBus = globalBus
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.frag_image, container, false);
    }

    override fun createViewState(): ImageFragViewState {
        return if (viewState == null) ImageFragViewState() else viewState
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.init()
    }

    override fun backPressedAction() {
        getCurrentActiity().onBackPressed()
    }

    override fun createPresenter(): ImageFragPresenter {
        return if(presenter == null) ImageFragPresenter(appComponent) else presenter
    }

    override fun onNewViewStateInstance() {
    }

}