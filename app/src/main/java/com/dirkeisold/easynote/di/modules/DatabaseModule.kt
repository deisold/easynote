package com.dirkeisold.easynote.di.modules

import android.content.Context
import com.dirkeisold.easynote.di.scope.ApplicationScope
import com.dirkeisold.easynote.persistence.DatabaseHelper
import com.dirkeisold.easynote.persistence.dao.NoteDao
import com.dirkeisold.easynote.persistence.dao.OrmLiteBaseDao
import com.dirkeisold.easynote.persistence.entities.NoteDB
import dagger.Module
import dagger.Provides
import timber.log.Timber
import java.sql.SQLException

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
 * Module providing db specific objects
 */
@Module
class DatabaseModule {

    @Provides
    @ApplicationScope
    internal fun provideDatabaseHelper(context: Context): DatabaseHelper =
            DatabaseHelper(context).apply { writableDatabase }

    @Provides
    @ApplicationScope
    internal fun provideNoteDao(databaseHelper: DatabaseHelper): NoteDao {
        try {
            with(OrmLiteBaseDao<NoteDB, Int>(
                    databaseHelper.connectionSource, NoteDB::class.java)) {
                initialize()
                return NoteDao(this)
            }
        } catch (e: SQLException) {
            Timber.e("error creating dao=" + e.message)
            throw RuntimeException(e)
        }
    }
}
