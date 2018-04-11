package com.dirkeisold.easynote.persistence

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.dirkeisold.easynote.persistence.entities.NoteDB
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
import com.j256.ormlite.support.ConnectionSource
import com.j256.ormlite.table.TableUtils
import timber.log.Timber
import java.sql.SQLException
import javax.inject.Inject

/**
 * Created by dirkeisold on 11.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 *
 * All rights reserved.
 */
class DatabaseHelper @Inject
constructor(context: Context) : OrmLiteSqliteOpenHelper(context, DATABASE_NAME, null, databaseVersion) {

    override fun onCreate(database: SQLiteDatabase, connectionSource: ConnectionSource) {
        Timber.d("creating database...")

        try {
            TableUtils.createTable(connectionSource, NoteDB::class.java)
        } catch (e: SQLException) {
            Timber.e("sql error=%s", e.localizedMessage)
        }

    }

    override fun onUpgrade(database: SQLiteDatabase, connectionSource: ConnectionSource, oldVersion: Int, newVersion: Int) {
        Timber.d("upgrading database...")

        try {
            TableUtils.dropTable<NoteDB, Any>(connectionSource, NoteDB::class.java, true)
        } catch (e: SQLException) {
            Timber.e("sql error=%s", e.localizedMessage)
        }
    }

    companion object {
        private val DATABASE_NAME = "datastore.db"
        private val databaseVersion = 1
    }
}