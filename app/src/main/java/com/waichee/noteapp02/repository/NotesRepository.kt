package com.waichee.noteapp02.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.waichee.noteapp02.database.DatabaseNote
import com.waichee.noteapp02.database.NotesDatabase
import com.waichee.noteapp02.database.asDomainModel
import com.waichee.noteapp02.domain.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class NotesRepository(private val database: NotesDatabase) {
    val notes: LiveData<List<Note>> = Transformations.map(database.noteDao.getNotes()) {
        it.asDomainModel()
    }

    suspend fun createNewNote(databaseNote: DatabaseNote): Long {
        var id = 0L
        withContext(Dispatchers.IO) {
            id = database.noteDao.insert(databaseNote)
            Timber.i("New note id is %d", id)
        }
        return id
    }

    suspend fun updateNote(databaseNote: DatabaseNote) {
        withContext(Dispatchers.IO) {
            database.noteDao.update(databaseNote)
            Timber.i("Note Updated, id = %d, Title = %s, Body = %s",
                databaseNote.id, databaseNote.title, databaseNote.body)
        }
    }

    suspend fun getNote(key: Long): Note? {
        return withContext(Dispatchers.IO) {
            database.noteDao.get(key)
        }
    }
}