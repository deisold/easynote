package com.dirkeisold.easynote.common

import android.app.Application
import com.dirkeisold.easynote.di.components.ApplicationComponent
import com.dirkeisold.easynote.di.components.DaggerApplicationComponent
import com.dirkeisold.easynote.di.modules.ApplicationModule

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
 * Custom Application holding the application scoped dagger graph
 */
class MyApplication : Application() {

    var applicationComponent: ApplicationComponent? = null
        private set

    override fun onCreate() {
        app = this

        super.onCreate()

        buildGraphAndInject()
    }

    protected fun buildGraphAndInject() {

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent?.inject(this)
    }

    companion object {
        var app: MyApplication? = null
            private set
    }
}
