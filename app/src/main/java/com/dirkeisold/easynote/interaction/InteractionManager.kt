package com.dirkeisold.easynote.interaction

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager

import com.dirkeisold.easynote.usecases.detail.DetailActivity
import com.dirkeisold.easynote.usecases.detail.view.ConfirmNoteDeletionFragment
import com.dirkeisold.easynote.usecases.detail.view.ConfirmNoteDeletionView

import javax.inject.Inject
import javax.inject.Provider

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
 * Interaction manager handling the start/show of new activities/dialogs
 */
class InteractionManager @Inject
constructor(private var activity: Activity, private var confirmNoteDeletionFragmentProvider: Provider<ConfirmNoteDeletionFragment>) {

    private val supportFragmentManager: FragmentManager by lazy { (activity as FragmentActivity).supportFragmentManager }

    fun startDetailActivityNewNote() {
        activity.startActivity(DetailActivity.buildIntentForNewNote(activity))
    }

    fun startDetailActivity(noteId: Int?) {
        activity.startActivity(DetailActivity.buildIntentAndShowNote(activity, noteId))
    }

    fun showNoteDeletionConfirmationDialog(): ConfirmNoteDeletionView =
            confirmNoteDeletionFragmentProvider.get().apply {
                show(supportFragmentManager, ConfirmNoteDeletionFragment::class.java.simpleName)
            }

    fun findNoteDeletionConfirmationDialog(): ConfirmNoteDeletionView? = supportFragmentManager
            .findFragmentByTag(ConfirmNoteDeletionFragment::class.java.simpleName) as? ConfirmNoteDeletionView
}
