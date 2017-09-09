package com.retail.kbv.retailapp.presenters.content.image

import com.google.firebase.database.*
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.model.DataItem
import com.retail.kbv.retailapp.presenters.BaseNetworkPresenter
import timber.log.Timber

class ImageFragPresenter(component: UserComponent): BaseNetworkPresenter<ImageFragView>() {
    private var database: FirebaseDatabase? = null
    private var dataBaseRef: DatabaseReference? = null

    init {
        component.inject(this)
        database = FirebaseDatabase.getInstance()
        dataBaseRef = database?.getReference()
    }

    private val itemsList: MutableList<DataItem> = mutableListOf()

    fun init() {
        val itemsListener = object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(itemsList) {
                    it.getValue<DataItem>(DataItem::class.java)
                }
                view.showContent(itemsList[0])
//                itemsList.forEach { item -> Timber.d("IMAGE = " + item.name) }
            }

            override fun onCancelled(p0: DatabaseError) {
                Timber.d(p0.toException(), "error")
            }
        }
        dataBaseRef?.child("items")?.addListenerForSingleValueEvent(itemsListener)
    }


}
