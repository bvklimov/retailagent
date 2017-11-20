package com.retail.kbv.retailapp.ui.fragments.content

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.databinding.FragBindingBinding
import com.retail.kbv.retailapp.model.DataItem
import com.retail.kbv.retailapp.presenters.content.image.ImageFragPresenter
import com.retail.kbv.retailapp.presenters.content.image.ImageFragView
import com.retail.kbv.retailapp.presenters.content.image.ImageFragViewState
import com.retail.kbv.retailapp.rxbus.RxBus
import com.retail.kbv.retailapp.ui.fragments.BaseFragment
import javax.inject.Inject
import javax.inject.Named

class BindingFragment: BaseFragment<ImageFragView, ImageFragPresenter, ImageFragViewState>(), ImageFragView {

    @field:[Inject Named("globalBus")]
    lateinit var globalBus: RxBus

    lateinit var binding: FragBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        rxBus = globalBus
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.frag_binding, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fragBindingText.text = "New text"
    }

    override fun backPressedAction() {
    }

    override fun onNewViewStateInstance() {
    }

    override fun createPresenter(): ImageFragPresenter {
        return if (presenter == null) ImageFragPresenter(appComponent) else presenter
    }

    override fun createViewState(): ImageFragViewState {
        return if (viewState == null) ImageFragViewState() else viewState
    }

    override fun showContent(dataItems: List<DataItem>) {
    }
}