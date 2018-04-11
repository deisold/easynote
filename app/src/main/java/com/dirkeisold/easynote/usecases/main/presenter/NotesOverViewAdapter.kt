package com.dirkeisold.easynote.usecases.main.presenter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dirkeisold.easynote.R
import com.dirkeisold.easynote.model.Note
import com.dirkeisold.easynote.usecases.main.view.NoteItemView
import javax.inject.Inject
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
 * Recycler adapter showing notes
 */
class NotesOverViewAdapter @Inject
constructor(val context: Activity) : RecyclerView.Adapter<NoteItemViewHolder>() {

    @Inject
    lateinit var noteItemPresenterProvider: Provider<NoteItemPresenter>

    private var data: MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder =
            NoteItemViewHolder(noteItemPresenterProvider, View.inflate(context,
                    R.layout.notes_overview_list_item, null) as NoteItemView)

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        holder.setupPresenter(getItem(position))
    }

    override fun onViewRecycled(holder: NoteItemViewHolder) {
        if (holder.adapterPosition != RecyclerView.NO_POSITION) {
            val presenter = holder.presenter
            presenter.detachView()
        }
    }

    fun setNotes(notes: List<Note>?) {
        data.clear()

        notes?.run { data.addAll(this) }
        notifyDataSetChanged()
    }

    private fun getItem(position: Int): Note = data[position]

    override fun getItemCount(): Int = data.size
}
