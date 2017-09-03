package com.retail.kbv.retailapp.ui.activities

import android.os.Bundle
import android.support.v4.graphics.drawable.TintAwareDrawable
import android.transition.TransitionSet
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.retail.kbv.retailapp.ui.fragments.BaseFragment
import com.retail.kbv.retailapp.ui.fragments.FragmentsFactory
import timber.log.Timber
import java.lang.Exception


abstract class FragActivity<V: MvpView, P: MvpNullObjectBasePresenter<V>>: BaseActivity<V, P>() {
    private val MAIN_FRAG_TAG = "MAIN_FRAG_TAG"

    protected var activeFragment: BaseFragment<*,*,*>? = null

    override fun onSaveInstanceState(outState: Bundle) {
        if (activeFragment != null) {
            outState.putString(MAIN_FRAG_TAG, activeFragment!!::class.simpleName)
        }
        super.onSaveInstanceState(outState)
    }

    fun switchToFragment(fragmentClass: Class<out BaseFragment<*, *, *>>,
                                position: Int,
                                data: Bundle?,
                                sharedElement: View?,
                                sharedAnchor: String?,
                                transitionSet: TransitionSet?) {
        val tag = fragmentClass.name
        var fragment: BaseFragment<*,*,*>
        try {
            fragment = if (data != null)
                FragmentsFactory.buildFragment(this, tag, data)
            else
                FragmentsFactory.buildFragment(this, tag)
            if (transitionSet != null) {
                fragment.setSharedElementEnterTransition(transitionSet)
            }
        } catch (ex: Exception) {
            Timber.e(ex)
            return
        }
        changeFragment(fragment, tag, position, sharedElement, sharedAnchor, transitionSet, true)
    }

    private fun changeFragment(fragment:BaseFragment <*,*,*>,
                               tag: String,
                               position: Int,
                               sharedElement: View?,
                               sharedAnchor: String?,
                               transitionSet: TransitionSet?,
                               addToBackStack: Boolean) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        if (sharedElement != null && sharedAnchor != null) {
            transaction.addSharedElement(sharedElement,sharedAnchor)
        }
        if (transitionSet != null) {
            fragment.setSharedElementEnterTransition(transitionSet)
        }
//        if (fragment is DrawerFragment) {
//            clearFragments(fragmentManager)
//            transaction.add(position, fragment, null)
//        } else {
            transaction.replace(position, fragment, tag)
//        }
        if (addToBackStack) {
            transaction.addToBackStack(tag)
        }
        transaction.commit()
        activeFragment = fragment

    }




}