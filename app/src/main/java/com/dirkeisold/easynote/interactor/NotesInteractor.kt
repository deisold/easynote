package com.dirkeisold.easynote.interactor

import com.dirkeisold.easynote.model.Note
import com.dirkeisold.easynote.persistence.dao.NoteDao
import com.dirkeisold.easynote.persistence.entities.NoteDB
import rx.Observable
import java.util.*
import javax.inject.Inject

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
 * the notes interactor wrapping db access with observables
 */
class NotesInteractor @Inject
constructor(protected var noteDao: NoteDao) : MVPInteractor {

    fun getAllNotes(): Observable<List<Note>> = Observable.create { subscriber ->
        with(ArrayList<Note>()) result@{

            noteDao.getAllNotes()
                    .forEach {
                        Note.getInstanceFrom(it)?.run { this@result.add(this) }
                    }
            subscriber.onNext(this@result)
        }
    }

    fun saveNote(noteDB: NoteDB): Observable<Note> = Observable.create { subscriber ->
        subscriber.onNext(Note.getInstanceFrom(noteDao.save(noteDB)))
    }


    fun findNote(noteId: Int): Observable<Note> = Observable.create { subscriber ->
        subscriber.onNext(Note.getInstanceFrom(noteDao.find(noteId)))
    }

    fun deleteNote(noteDB: NoteDB): Observable<Int> = Observable.create { subscriber -> subscriber.onNext(noteDao.delete(noteDB)) }

    fun deleteNote(noteId: Int): Observable<Int> = Observable.create { subscriber -> subscriber.onNext(noteDao.deleteById(noteId)) }
}
