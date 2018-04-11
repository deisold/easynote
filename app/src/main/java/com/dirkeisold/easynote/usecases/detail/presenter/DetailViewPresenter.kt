package com.dirkeisold.easynote.usecases.detail.presenter

import android.app.Activity
import android.os.Bundle
import com.dirkeisold.easynote.R
import com.dirkeisold.easynote.common.Utils
import com.dirkeisold.easynote.common.ifNotNull
import com.dirkeisold.easynote.interaction.InteractionManager
import com.dirkeisold.easynote.interactor.NotesInteractor
import com.dirkeisold.easynote.model.Note
import com.dirkeisold.easynote.persistence.entities.NoteDB
import com.dirkeisold.easynote.usecases.detail.view.ConfirmNoteDeletionView
import com.dirkeisold.easynote.usecases.detail.view.DetailView
import com.dirkeisold.easynote.usecases.main.presenter.MVPAbstractLoadingPresenter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import timber.log.Timber
import java.util.*
import javax.inject.Inject

/**
 * Created by dirkeisold on 12.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 *
 *
 * The presenter for the detail view of a noteFromDb.
 */
class DetailViewPresenter @Inject
constructor() : MVPAbstractLoadingPresenter<DetailView>(), DetailView.Callbacks {

    private var view: DetailView? = null

    private var noteFromDb: Note? = null
    private var currentNote: Note? = null

    private val KEY_NOTE = "key.note"

    @Inject
    lateinit var activity: Activity

    @Inject
    lateinit var notesInteractor: NotesInteractor

    @Inject
    lateinit var confirmNoteDeletionPresenter: ConfirmNoteDeletionPresenter

    @Inject
    lateinit var interactionManager: InteractionManager

    override fun attachView(view: DetailView) {
        this.view = view
        view.setCallbacks(this)

        populateUI()

        interactionManager.findNoteDeletionConfirmationDialog()?.let {
            setupConfirmNoteDeletionPresenter(it)
        }
    }

    override fun detachView() {
        view?.removeCallbacks()
        confirmNoteDeletionPresenter.detachView()
        view = null

        super.detachView()
    }

    private fun populateUI() {
        ifNotNull(if (currentNote == null) noteFromDb else currentNote, view) { n, v ->
            if (n.id == null)
                v.setActionBarTitle(activity.getString(R.string.detail_activity_actionbar_title_new))
            else
                v.setActionBarTitle(activity.getString(R.string.detail_activity_actionbar_title, n.title
                        ?: ""))

            v.setTitle(n.title)
            v.setText(n.text)
            v.setLastModified(Utils.getFormattedDate(activity, R.string.detail_activity_last_modified, n.lastModified))

            v.setSettingDataFinished()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        // only the changed note needs to be saved
        currentNote?.run {
            outState.putSerializable(KEY_NOTE, this)
        }
    }

    override fun onRestoreSavedInstanceState(savedInstanceState: Bundle) {
        currentNote = savedInstanceState.getSerializable(KEY_NOTE) as Note
        populateUI()
    }

    fun createNewNote() {
        noteFromDb = Note().apply { lastModified = Date() }
        populateUI()
    }

    fun load(noteId: Int) {
        addSubscription(
                notesInteractor.findNote(noteId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ this.successLoadNote(it) }, { this.error(it) })
        )
    }

    private fun successLoadNote(note: Note?) {
        this.noteFromDb = note

        populateUI()

        ifNotNull(view, note) { v, _ -> v.showDelete() }
    }

    private fun error(throwable: Throwable) {
        Timber.e(throwable, "error fetching data: %s", throwable.toString())
    }

    override fun onNoteChanged(title: String, text: String) {
        if (currentNote == null) currentNote = noteFromDb?.copy()

        currentNote?.title = title
        currentNote?.text = text

        val showSave = Utils.equals(noteFromDb?.title, currentNote?.title).not() ||
                Utils.equals(noteFromDb?.text, currentNote?.text).not()

        view?.run {
            if (showSave) showSave()
            else hideSave()
        }
    }

    override fun onSaveClicked() {
        ifNotNull(if (currentNote == null) noteFromDb else currentNote, view) { n, v ->
            n.lastModified = Date()

            addSubscription(
                    notesInteractor.saveNote(NoteDB.getInstanceFrom(n))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe({ this.successSaveNote() }, { this.error(it) })
            )
        }
    }

    private fun successSaveNote() {
        activity.finish()
    }

    override fun onDeleteClicked() {
        setupConfirmNoteDeletionPresenter(interactionManager.showNoteDeletionConfirmationDialog())
    }

    private fun setupConfirmNoteDeletionPresenter(view: ConfirmNoteDeletionView?) {
        ifNotNull(view, noteFromDb) { v, n ->
            with(confirmNoteDeletionPresenter) {
                attachView(v)
                noteId = n.id
            }
        }
    }

    private fun successDeleteNote(integer: Int?) {
        activity.finish()
    }
}
