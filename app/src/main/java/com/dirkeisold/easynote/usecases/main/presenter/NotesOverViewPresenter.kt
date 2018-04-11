package com.dirkeisold.easynote.usecases.main.presenter

import com.dirkeisold.easynote.common.ifNotNull
import com.dirkeisold.easynote.interaction.InteractionManager
import com.dirkeisold.easynote.interactor.NotesInteractor
import com.dirkeisold.easynote.model.Note
import com.dirkeisold.easynote.usecases.main.view.NotesOverView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject


/**
 * Created by dirkeisold on 11.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
class NotesOverViewPresenter @Inject
constructor() : MVPAbstractLoadingPresenter<NotesOverView>(), NotesOverView.Callbacks {

    @Inject
    lateinit var notesInteractor: NotesInteractor

    @Inject
    lateinit var interactionManager: InteractionManager

    private var notes: List<Note>? = null
    private var view: NotesOverView? = null

    override fun attachView(view: NotesOverView) {
        this.view = view
        this.view?.setCallbacks(this)

        populateUI()
    }

    override fun detachView() {
        view?.removeCallbacks()
        view = null

        super.detachView()
    }

    private fun populateUI() {
        ifNotNull(notes, view) { n, v -> v.setNotes(n) }
    }

    override fun onAddNewNoteClicked() = interactionManager.startDetailActivityNewNote()

    fun load() {
        addSubscription(
                notesInteractor.getAllNotes()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ this.success(it) }, { this.error(it) })
        )
    }

    private fun error(throwable: Throwable) =
            Timber.e(throwable, "error fetching data: %s", throwable.toString())

    private fun success(notes: List<Note>) {
        Timber.d("success: got #=%d notes from interactor", notes.size)

        this.notes = notes

        populateUI()
    }
}
