package com.retail.kbv.retailapp.presenters.content.image

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.presenters.BaseNetworkPresenter
import timber.log.Timber

class ImageFragPresenter(component: UserComponent): BaseNetworkPresenter<ImageFragView>() {
    private var database: FirebaseDatabase? = null
    private var dataBaseRef: DatabaseReference? = null

    init {
        component.inject(this)
        database = FirebaseDatabase.getInstance()
        dataBaseRef = database?.getReference("items")
    }


    private var auth: FirebaseAuth? = null

    fun init() {
        dataBaseRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Timber.d("Image data: " + dataSnapshot.getValue(String::class.java))
            }

            override fun onCancelled(error: DatabaseError) {
                Timber.d(error.toException(), "Image data error: ")
            }
        })
    }


}
