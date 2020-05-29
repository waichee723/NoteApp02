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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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

class NoteDetailViewModel(note: Note, application: Application) : AndroidViewModel(application), Observable {


    private val callbacks: PropertyChangeRegistry = PropertyChangeRegistry()

    override fun addOnPropertyChangedCallback(
        callback: OnPropertyChangedCallback) {
        callbacks.add(callback)
    }

    override fun removeOnPropertyChangedCallback(
        callback: OnPropertyChangedCallback) {
        callbacks.remove(callback)
    }

    /**
     * Notifies observers that all properties of this instance have changed.
     */
    fun notifyChange() {
        callbacks.notifyCallbacks(this, 0, null)
    }


    private val notesRepository = NotesRepository(getDatabase(application))

    private val viewModelJob = SupervisorJob()
    private val viewModelScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    private val _selectedNote = MutableLiveData<Note>()

    @Bindable
    var selectedNote: LiveData<Note> = _selectedNote
        get() = _selectedNote
    



    init {
        _selectedNote.value = note
    }


    private val _navigateToNoteList = MutableLiveData<Boolean?>()
    val navigateToNoteList: LiveData<Boolean?>
        get() = _navigateToNoteList

    fun onSave() {
        viewModelScope.launch {
            notesRepository.updateNote(
                _selectedNote.value!!
            )
            Timber.i("Saved")
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



    class Factory(val note: Note, val app: Application): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NoteDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NoteDetailViewModel(note, app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}