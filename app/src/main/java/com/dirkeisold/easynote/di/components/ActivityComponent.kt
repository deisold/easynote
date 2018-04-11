package com.dirkeisold.easynote.di.components

import com.dirkeisold.easynote.di.modules.ActivityModule
import com.dirkeisold.easynote.di.scope.ActivityScope
import com.dirkeisold.easynote.usecases.detail.DetailActivity
import com.dirkeisold.easynote.usecases.main.MainActivity
import com.dirkeisold.easynote.usecases.main.view.NotesOverFragment

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
 * The activity component providing activity scoped objects
 */
@ActivityScope
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(notesOverFragment: NotesOverFragment)

    fun inject(detailActivity: DetailActivity)
}
