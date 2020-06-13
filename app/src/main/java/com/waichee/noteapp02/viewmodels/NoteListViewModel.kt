package com.waichee.noteapp02.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waichee.noteapp02.database.DatabaseNote
import com.waichee.noteapp02.database.NoteDao
import com.waichee.noteapp02.database.asDomainModel
import com.waichee.noteapp02.database.getDatabase
import com.waichee.noteapp02.domain.Note
import com.waichee.noteapp02.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.IllegalArgumentException

class NoteListViewModel(application: Application): AndroidViewModel(application) {

    private val notesRepository = NotesRepository(getDatabase(application))

    val noteList = notesRepository.notes

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _navigateToNoteDetail = MutableLiveData<Long>()
    val navigateToNoteDetail: LiveData<Long>
        get() = _navigateToNoteDetail

    private val _displayAlert = MutableLiveData<Boolean>()
    val displayAlert: LiveData<Boolean>
        get() = _displayAlert


    // Methods for layout file to access by data binding

    fun onNewNote() {
        viewModelScope.launch {
            val newNote = DatabaseNote()
            val newNoteId = notesRepository.createNewNote(newNote)
            Timber.i("New note created, id = %d, title = %s, body = %s", newNoteId, newNote.title, newNote.body)

            _navigateToNoteDetail.value = newNoteId
        }
    }

    fun deleteAllNote() {
        viewModelScope.launch {
            notesRepository.deleteAllNote()
            _displayAlert.value = true
        }
    }

    // Navigation

    fun displayNoteDetail(noteId: Long) {
        _navigateToNoteDetail.value = noteId
    }

    fun doneNavigateToDetail() {
        _navigateToNoteDetail.value = null
    }

    fun doneDisplayAlert() {
        _displayAlert.value = null
    }

    // Job

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    // ViewModel Factory

    class Factory(val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteListViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}