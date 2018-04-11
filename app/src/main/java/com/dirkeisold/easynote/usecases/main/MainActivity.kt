package com.dirkeisold.easynote.usecases.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dirkeisold.easynote.R
import com.dirkeisold.easynote.common.MyApplication
import com.dirkeisold.easynote.di.components.ActivityComponent
import com.dirkeisold.easynote.di.components.DaggerActivityComponent
import com.dirkeisold.easynote.di.interfaces.ActivityGraphDelegate
import com.dirkeisold.easynote.di.modules.ActivityModule
import com.dirkeisold.easynote.usecases.main.presenter.NotesOverViewPresenter
import com.dirkeisold.easynote.usecases.main.view.NotesOverFragment
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
 * Activity hosting the a fragment showing all notes persisted on db.
 *
 * Building and providing the activity dependency graph.
 */
class MainActivity : AppCompatActivity(), ActivityGraphDelegate {

    override var activityGraph: ActivityComponent? = null

    private var notesOverFragment: NotesOverFragment? = null

    @Inject
    lateinit var notesOverViewPresenter: NotesOverViewPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        injectDI()

        setContentView(R.layout.activity_main)

        notesOverFragment = supportFragmentManager
                .findFragmentById(R.id.fragment_notes_overview) as NotesOverFragment
    }

    override fun onStart() {
        super.onStart()

        notesOverFragment?.let { notesOverViewPresenter.attachView(it) }
    }

    override fun onResume() {
        super.onResume()

        notesOverViewPresenter.load()
    }

    override fun onStop() {
        notesOverViewPresenter.detachView()
        super.onStop()
    }

    private fun injectDI() {
        activityGraph = DaggerActivityComponent
                .builder()
                .applicationComponent(MyApplication.app?.applicationComponent)
                .activityModule(ActivityModule(this))
                .build()

        activityGraph?.inject(this)
    }
}
