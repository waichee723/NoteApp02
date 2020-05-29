package com.waichee.noteapp02.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.waichee.noteapp02.database.NotesDatabase
import com.waichee.noteapp02.database.asDomainModel
import com.waichee.noteapp02.domain.Note
import com.waichee.noteapp02.domain.asDatabaseNote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class NotesRepository(private val database: NotesDatabase) {
    val notes: LiveData<List<Note>> = Transformations.map(database.noteDao.getNotes()) {
        it.asDomainModel()
    }

    suspend fun createNewNote(note: Note) {
        withContext(Dispatchers.IO) {
            database.noteDao.insert(note.asDatabaseNote())
            Timber.i("New note created")
        }
    }

    suspend fun updateNote(note: Note) {
        withContext(Dispatchers.IO) {
            database.noteDao.update(note.asDatabaseNote())
        }
    }
}