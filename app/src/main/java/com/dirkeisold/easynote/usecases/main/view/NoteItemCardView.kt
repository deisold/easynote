package com.dirkeisold.easynote.usecases.main.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.dirkeisold.easynote.R
import kotlinx.android.synthetic.main.note_list_item.view.*


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
 * Custom View showing a note data using a card view
 */
class NoteItemCardView : FrameLayout, NoteItemView, View.OnClickListener {

    private val titleTextView: TextView by lazy { title }
    private val textTextView: TextView by lazy { text }
    private val lastModified: TextView by lazy { last_modified }

    private var callbacks: NoteItemView.Callbacks? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.note_list_item, this)
        layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)

        setOnClickListener(this)
    }

    override fun setTitle(title: String) {
        titleTextView.text = title
    }

    override fun setText(text: String) {
        textTextView.text = text
    }

    override fun setLastModified(lastModified: String) {
        this.lastModified.text = lastModified
    }

    override fun setCallbacks(callbacks: NoteItemView.Callbacks) {
        this.callbacks = callbacks
    }

    override fun removeCallbacks() {
        callbacks = null
    }

    override fun onClick(v: View) {
        callbacks?.onNoteClicked()
    }
}
