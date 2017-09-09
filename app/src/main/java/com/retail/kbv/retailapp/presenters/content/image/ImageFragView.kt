package com.retail.kbv.retailapp.presenters.content.image

import com.retail.kbv.retailapp.model.DataItem
import com.retail.kbv.retailapp.presenters.NetworkRequestView


interface ImageFragView: NetworkRequestView {
    fun showContent(dataItem: DataItem)
}
