package com.dirkeisold.easynote.usecases.detail.view

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
interface ConfirmNoteDeletionView : MVPView {

    interface Callbacks {

        fun onYesClicked()

        fun onNoClicked()
    }

    fun setCallbacks(callbacks: Callbacks)

    fun removeCallbacks()
}
