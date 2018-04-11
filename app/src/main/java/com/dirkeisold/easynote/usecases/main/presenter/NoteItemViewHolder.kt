package com.dirkeisold.easynote.usecases.main.presenter

import android.support.v7.widget.RecyclerView
import android.view.View

import com.dirkeisold.easynote.model.Note
import com.dirkeisold.easynote.usecases.main.view.NoteItemView

import javax.inject.Provider

/**
 * Created by dirkeisold on 11.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 *
 *
 * The recycler view holder for notes
 */
class NoteItemViewHolder(noteItemProviderProvider: Provider<NoteItemPresenter>,
                         private val mvpView: NoteItemView) : RecyclerView.ViewHolder(mvpView as View) {

    val presenter: NoteItemPresenter = noteItemProviderProvider.get()

    fun setupPresenter(note: Note) {
        presenter.setData(note)
        presenter.attachView(mvpView)
    }
}
