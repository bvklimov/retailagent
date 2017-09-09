package com.retail.kbv.retailapp.presenters.content

import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.presenters.BaseNetworkPresenter

class ContentActivityPresenter(component: UserComponent): BaseNetworkPresenter<ContentActivityView>() {
    init {
        component.inject(this)
    }
}
