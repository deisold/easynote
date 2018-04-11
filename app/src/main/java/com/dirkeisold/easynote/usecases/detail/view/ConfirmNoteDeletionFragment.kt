package com.dirkeisold.easynote.usecases.detail.view

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import com.dirkeisold.easynote.R

/**
 * Created by dirkeisold on 12.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
class ConfirmNoteDeletionFragment : DialogFragment(), ConfirmNoteDeletionView {

    private var callbacks: ConfirmNoteDeletionView.Callbacks? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = activity
        return AlertDialog.Builder(activity!!)
                .setTitle(activity.resources.getString(R.string.confirmation_dialog_title))
                .setMessage(activity.resources.getString(R.string.confirmation_dialog_message))
                .setNegativeButton(android.R.string.no) { dialog, which ->
                    if (callbacks != null) {
                        callbacks!!.onNoClicked()
                    }
                }
                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    if (callbacks != null) {
                        callbacks!!.onYesClicked()
                    }
                }
                .create()
    }

    override fun setCallbacks(callbacks: ConfirmNoteDeletionView.Callbacks) {
        this.callbacks = callbacks
    }

    override fun removeCallbacks() {
        callbacks = null
    }
}
