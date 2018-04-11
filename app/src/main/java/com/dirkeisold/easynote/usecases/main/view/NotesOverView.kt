package com.dirkeisold.easynote.usecases.main.view

import com.dirkeisold.easynote.model.Note

/**
 * Created by dirkeisold on 11.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
interface NotesOverView : MVPView {

    interface Callbacks {
        fun onAddNewNoteClicked()
    }

    fun setNotes(notes: List<Note>)

    fun setCallbacks(callbacks: Callbacks)

    fun removeCallbacks()
}
