package com.retail.kbv.retailapp.presenters.content.image

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.retail.kbv.retailapp.injections.UserComponent
import com.retail.kbv.retailapp.observables.dataBaseObservable
import com.retail.kbv.retailapp.presenters.BaseNetworkPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ImageFragPresenter(component: UserComponent): BaseNetworkPresenter<ImageFragView>() {
    private var database: FirebaseDatabase? = null
    private var dataBaseRef: DatabaseReference? = null

    init {
        component.inject(this)
        database = FirebaseDatabase.getInstance()
        dataBaseRef = database?.getReference()
    }


    fun init() {
        dataBaseObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({itemsList ->
                    view.showContent(itemsList)
                })
    }
}
