package com.dirkeisold.easynote.di.modules

import android.app.Activity

import com.dirkeisold.easynote.usecases.detail.view.ConfirmNoteDeletionFragment

import dagger.Module
import dagger.Provides

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
 * Module providing activity scope objects
 */
@Module
class ActivityModule(private val mActivity: Activity) {

    @Provides
    internal fun provideActivity(): Activity = mActivity

    @Provides
    internal fun provideConfirmNoteDeletionFragment(): ConfirmNoteDeletionFragment = ConfirmNoteDeletionFragment()
}
