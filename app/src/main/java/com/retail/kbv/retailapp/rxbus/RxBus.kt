package com.retail.kbv.retailapp.rxbus

import com.retail.kbv.retailapp.rxbus.events.RxBusEvent
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

public class RxBus() {
    val bus: PublishSubject<RxBusEvent> = PublishSubject.create()

    fun send(event: RxBusEvent) {
        bus.onNext(event)
    }

    fun toObservable(): Observable<RxBusEvent> {
        return bus
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}