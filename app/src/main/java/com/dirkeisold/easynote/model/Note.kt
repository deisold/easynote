package com.dirkeisold.easynote.model

import com.dirkeisold.easynote.persistence.entities.NoteDB
import java.io.Serializable
import java.util.*

/**
 * Created by dirkeisold on 11.03.16.
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 * All rights reserved.
 *
 * The note model
 */
data class Note(
        var id: Int? = null,
        var title: String? = null,
        var lastModified: Date? = null,
        var text: String? = null
) : Serializable {

    companion object {
        fun getInstanceFrom(noteDb: NoteDB?): Note? =
                if (noteDb == null) null
                else
                    Note(id = noteDb.id, title = noteDb.title, lastModified = noteDb.lastModified, text = noteDb.text)
    }
}
