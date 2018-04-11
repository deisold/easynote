package com.dirkeisold.easynote.usecases.main.view

import android.content.Context
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dirkeisold.easynote.R
import com.dirkeisold.easynote.common.or
import com.dirkeisold.easynote.di.interfaces.ActivityGraphDelegate
import com.dirkeisold.easynote.model.Note
import com.dirkeisold.easynote.usecases.main.presenter.NotesOverViewAdapter
import kotlinx.android.synthetic.main.notes_overview_fragment.*
import javax.inject.Inject

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
 * Fragment showing all notes persisted on db.
 */
class NotesOverFragment : Fragment(), NotesOverView {

    private val recyclerView: RecyclerView by lazy { notes_list }
    private val addNewNoteButton: FloatingActionButton by lazy { add_new_note }

    @Inject
    lateinit var notesOverViewAdapter: NotesOverViewAdapter

    private var callbacks: NotesOverView.Callbacks? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.notes_overview_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = notesOverViewAdapter

        addNewNoteButton.setOnClickListener { callbacks?.onAddNewNoteClicked() }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        (context as? ActivityGraphDelegate)?.run {
            activityGraph?.inject(this@NotesOverFragment)
        }.or { throw IllegalStateException("Hosting activity must implement ActivityGraphDelegate") }
    }

    override fun setNotes(notes: List<Note>) = notesOverViewAdapter.setNotes(notes)

    override fun setCallbacks(callbacks: NotesOverView.Callbacks) {
        this.callbacks = callbacks
    }

    override fun removeCallbacks() {
        callbacks = null
    }
}
