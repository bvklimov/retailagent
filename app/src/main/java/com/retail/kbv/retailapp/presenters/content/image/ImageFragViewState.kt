package com.retail.kbv.retailapp.presenters.content.image

import android.os.Bundle
import com.hannesdorfmann.mosby3.mvp.viewstate.RestorableViewState

class ImageFragViewState : RestorableViewState<ImageFragView> {
    override fun apply(view: ImageFragView?, retained: Boolean) {
    }

    override fun restoreInstanceState(`in`: Bundle?): RestorableViewState<ImageFragView>? {
        return null
    }

    override fun saveInstanceState(out: Bundle) {
    }
}
