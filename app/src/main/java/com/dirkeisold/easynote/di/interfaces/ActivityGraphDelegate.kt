package com.dirkeisold.easynote.di.interfaces

import com.dirkeisold.easynote.di.components.ActivityComponent

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
 * Interface allowing access to the activity scope dagger graph
 */
interface ActivityGraphDelegate {
    val activityGraph: ActivityComponent?
}
