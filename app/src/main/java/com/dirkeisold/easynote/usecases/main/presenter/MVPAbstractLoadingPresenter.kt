package com.dirkeisold.easynote.usecases.main.presenter

import com.dirkeisold.easynote.usecases.main.view.MVPView

import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Created by dirkeisold on 13.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 *
 *
 *
 *
 * Abstract presenter handling unsubscription on all subscription passed along
 */
abstract class MVPAbstractLoadingPresenter<in T : MVPView> : MVPPresenter<T> {

    private var compositeSubscription: CompositeSubscription? = null

    override fun detachView() {
        unSubscribeAll()
    }

    /**
     * Clears and unsubscribes all subscriptions added before
     */
    fun unSubscribeAll() {
        compositeSubscription?.clear()
    }

    /**
     * Adds a subscription to a composite subscription which is cleared and unsubscribed onDestroy
     *
     * @param subscription a subscription
     */
    protected fun addSubscription(subscription: Subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription!!.add(subscription)
    }
}
