package com.waichee.noteapp02.viewmodels

import android.app.Application
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.PropertyChangeRegistry
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waichee.noteapp02.database.getDatabase
import com.waichee.noteapp02.domain.Note
import com.waichee.noteapp02.domain.asDatabaseNote
import com.waichee.noteapp02.repository.NotesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.IllegalArgumentException

class NoteDetailViewModel(noteId: Long, application: Application) : AndroidViewModel(application) {

    private val notesRepository = NotesRepository(getDatabase(application))

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _selectedNote = MutableLiveData<Note>()
    val selectedNote: LiveData<Note>
        get() = _selectedNote

    init {
        setSelectedNote(noteId)
    }


    private val _navigateToNoteList = MutableLiveData<Boolean?>()
    val navigateToNoteList: LiveData<Boolean?>
        get() = _navigateToNoteList

    fun setSelectedNote(noteId: Long) {
        viewModelScope.launch {
            _selectedNote.value = notesRepository.getNote(noteId)
        }
    }

    fun onSave() {
            updateNote()
    }


    fun updateNote() {
        viewModelScope.launch {
            notesRepository.updateNote(
                selectedNote.value!!.asDatabaseNote()
            )
            _navigateToNoteList.value = true
        }
    }

    fun doneNavigateToNoteList() {
        _navigateToNoteList.value = null
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }



    class Factory(val noteId: Long, val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteDetailViewModel(noteId, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}