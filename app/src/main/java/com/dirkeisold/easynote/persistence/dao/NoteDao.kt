package com.dirkeisold.easynote.persistence.dao

import com.dirkeisold.easynote.persistence.entities.NoteDB
import com.j256.ormlite.dao.Dao
import com.j256.ormlite.dao.RuntimeExceptionDao

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
 * DAO for notes
 */
class NoteDao(dao: Dao<NoteDB, Int>) : RuntimeExceptionDao<NoteDB, Int>(dao) {

    @Synchronized
    fun getAllNotes() = queryForAll()

    @Synchronized
    fun save(noteDB: NoteDB): NoteDB {
        createOrUpdate(noteDB)

        return noteDB
    }

    @Synchronized
    fun find(id: Int): NoteDB? = queryForId(id)


    @Synchronized
    fun deleteNote(noteDB: NoteDB): Int = delete(noteDB)
}
