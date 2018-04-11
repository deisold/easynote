package com.dirkeisold.easynote.usecases.main.presenter

import com.dirkeisold.easynote.usecases.main.view.MVPView

/**
 * Created by dirkeisold on 11.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
interface MVPPresenter<in T : MVPView> {

    /**
     * Attach the view to this presenter
     *
     * @param view
     */
    fun attachView(view: T)

    /**
     * Detach the view from this presenter
     */
    fun detachView()
}
