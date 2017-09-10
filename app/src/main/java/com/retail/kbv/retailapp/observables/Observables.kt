package com.retail.kbv.retailapp.observables

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.retail.kbv.retailapp.model.DataItem
import io.reactivex.Observable

fun dataBaseObservable(): Observable<List<DataItem>> {
    return Observable.create { subscriber ->
        val dataBaseRef = FirebaseDatabase.getInstance()?.reference
        val itemsList: MutableList<DataItem> = mutableListOf()
        dataBaseRef?.child("items")?.addListenerForSingleValueEvent( object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataSnapshot.children.mapNotNullTo(itemsList) {
                    it.getValue<DataItem>(DataItem::class.java)
                }
                subscriber.onNext(itemsList)
            }

            override fun onCancelled(error: DatabaseError) {
                subscriber.onError(error.toException())
            }
        })

    }
}