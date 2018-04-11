package com.dirkeisold.easynote.di.components

import android.content.Context

import com.dirkeisold.easynote.common.MyApplication
import com.dirkeisold.easynote.di.modules.ApplicationModule
import com.dirkeisold.easynote.di.modules.DatabaseModule
import com.dirkeisold.easynote.di.scope.ApplicationScope
import com.dirkeisold.easynote.persistence.dao.NoteDao

import dagger.Component

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
 * The application component providing application scoped objects
 */
@ApplicationScope
@Component(modules = arrayOf(ApplicationModule::class, DatabaseModule::class))
interface ApplicationComponent {

    fun provideContext(): Context

    fun provideNoteDao(): NoteDao

    fun inject(myApplication: MyApplication)

}
