package com.dirkeisold.easynote.usecases.main.view

/**
 * Created by dirkeisold on 11.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
interface NoteItemView : MVPView {

    interface Callbacks {
        fun onNoteClicked()
    }

    fun setTitle(title: String)

    fun setText(text: String)

    fun setLastModified(lastModified: String)

    fun setCallbacks(callbacks: Callbacks)

    fun removeCallbacks()
}
