package com.dirkeisold.easynote.di.modules

import android.content.Context

import com.dirkeisold.easynote.common.MyApplication
import com.dirkeisold.easynote.di.scope.ApplicationScope

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
 * Module providing application scope objects
 */
@Module
class ApplicationModule(context: Context) {

    private val appContext: Context = context.applicationContext

    @Provides
    @ApplicationScope
    internal fun provideApplicationContext(): Context = this.appContext

    @Provides
    @ApplicationScope
    internal fun provideMyApplication(context: Context): MyApplication = context.applicationContext as MyApplication

}
