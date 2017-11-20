package com.retail.kbv.retailapp.ui.adapters

import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.retail.kbv.retailapp.R
import com.retail.kbv.retailapp.model.DataItem
import kotlinx.android.synthetic.main.item_data.view.*

class ItemAdapter(private val dataItems: List<DataItem>) : PagerAdapter() {
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return false
    }

    override fun getCount(): Int {
        return dataItems.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val context = container.context
        val view = LayoutInflater.from(context)
                .inflate(R.layout.item_data, container, false)
        val item = dataItems[position]
        Glide.with(context)
                .load(item.image)
                .into(view.itemData_image)
        view.itemData_description.text = item.name
        container.addView(view)
        return view
    }
}