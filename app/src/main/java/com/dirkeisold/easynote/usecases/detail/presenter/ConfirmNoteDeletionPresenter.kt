package com.dirkeisold.easynote.usecases.detail.presenter

import android.app.Activity
import com.dirkeisold.easynote.common.ifNotNull
import com.dirkeisold.easynote.interactor.NotesInteractor
import com.dirkeisold.easynote.usecases.detail.view.ConfirmNoteDeletionView
import com.dirkeisold.easynote.usecases.main.presenter.MVPAbstractLoadingPresenter
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action0
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by dirkeisold on 12.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
class ConfirmNoteDeletionPresenter @Inject
constructor(private var activity: Activity,
            private var notesInteractor: NotesInteractor) : MVPAbstractLoadingPresenter<ConfirmNoteDeletionView>(), ConfirmNoteDeletionView.Callbacks {

    private var view: ConfirmNoteDeletionView? = null
    var noteId: Int? = null

    override fun onYesClicked() {
        ifNotNull(view, noteId) { v, id ->
            addSubscription(
                    notesInteractor.deleteNote(id)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ activity.finish() }, { this.error(it) })
            )
        }
    }

    private fun error(throwable: Throwable) {
        Timber.e(throwable, "error fetching data: %s", throwable.toString())
    }

    override fun onNoClicked() {
        // do nothing
    }

    override fun attachView(view: ConfirmNoteDeletionView) {
        view.let {
            this.view = it
            it.setCallbacks(this)
        }
    }

    override fun detachView() {
        view?.removeCallbacks()
        view = null
    }
}
