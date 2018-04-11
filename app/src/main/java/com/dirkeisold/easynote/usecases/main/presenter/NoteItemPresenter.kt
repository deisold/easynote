package com.dirkeisold.easynote.usecases.main.presenter

import android.app.Activity

import com.dirkeisold.easynote.R
import com.dirkeisold.easynote.common.Utils
import com.dirkeisold.easynote.common.ifNotNull
import com.dirkeisold.easynote.interaction.InteractionManager
import com.dirkeisold.easynote.model.Note
import com.dirkeisold.easynote.usecases.main.view.NoteItemView

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
class NoteItemPresenter @Inject
constructor() : MVPPresenter<NoteItemView>, NoteItemView.Callbacks {

    private var note: Note? = null
    private var view: NoteItemView? = null

    @Inject
    lateinit var interactionManager: InteractionManager

    @Inject
    lateinit var activity: Activity

    override fun attachView(view: NoteItemView) {
        this.view = view
        this.view?.setCallbacks(this)

        populateUI()
    }

    override fun detachView() {
        view?.removeCallbacks()
        view = null
    }

    fun setData(note: Note) {
        this.note = note
    }

    private fun populateUI() {
        ifNotNull(note, view) { n, v ->
            v.setTitle(n.title!!)
            v.setText(n.text!!)
            v.setLastModified(Utils.getFormattedDate(activity, R.string.detail_activity_last_modified, n.lastModified))
        }
    }

    override fun onNoteClicked() {
        note?.let { interactionManager.startDetailActivity(it.id) }
    }
}
