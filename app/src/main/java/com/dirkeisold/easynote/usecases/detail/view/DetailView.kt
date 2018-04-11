package com.dirkeisold.easynote.usecases.detail.view

import android.os.Bundle
import com.dirkeisold.easynote.usecases.main.view.MVPView

/**
 * Created by dirkeisold on 12.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
interface DetailView : MVPView {

    fun setSettingDataFinished()

    fun showSave()

    fun hideSave()

    interface Callbacks {

        fun onNoteChanged(title: String, text: String)

        fun onSaveClicked()

        fun onDeleteClicked()

        fun onSaveInstanceState(outState: Bundle)

        fun onRestoreSavedInstanceState(savedInstanceState: Bundle)
    }

    fun setActionBarTitle(title: String?)

    fun setTitle(title: String?)

    fun setText(text: String?)

    fun setLastModified(lastModified: String)

    fun showDelete()

    fun setCallbacks(callbacks: Callbacks)

    fun removeCallbacks()
}
