package com.dirkeisold.easynote.persistence.entities

import com.dirkeisold.easynote.model.Note
import com.j256.ormlite.field.DatabaseField
import com.j256.ormlite.table.DatabaseTable
import java.util.*

/**
 * Created by dirkeisold on 11.03.16.
 *
 *
 * Copyright (c) 2018, Dirk Eisold
 *
 * All rights reserved.
 *
 * The db pojo for note
 */
@DatabaseTable(tableName = "notes")
class NoteDB {
    @DatabaseField(columnName = COLUMN_ID, generatedId = true)
    var id: Int? = null

    @DatabaseField(columnName = COLUMN_LANGUAGE_LAST_MODIFIED)
    var lastModified = Date()

    @DatabaseField(columnName = COLUMN_LANGUAGE_TITLE)
    var title: String? = null

    @DatabaseField(columnName = COLUMN_ORIGINAL_TEXT)
    var text: String? = null

    override fun toString(): String {
        return "NoteDB{id=$id, lastModified=$lastModified, title='$title\', text='$text\'}"
    }

    companion object {
        const val COLUMN_ID: String = "id"
        const val COLUMN_LANGUAGE_LAST_MODIFIED: String = "last_modified"
        const val COLUMN_LANGUAGE_TITLE: String = "title"
        const val COLUMN_ORIGINAL_TEXT: String = "text"

        fun getInstanceFrom(note: Note): NoteDB = NoteDB().apply {
            id = note.id
            title = note.title
            lastModified = note.lastModified!!
            text = note.text
        }
    }
}
