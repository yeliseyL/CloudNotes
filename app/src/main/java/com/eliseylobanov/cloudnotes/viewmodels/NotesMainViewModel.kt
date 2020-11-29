package com.eliseylobanov.cloudnotes.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import com.eliseylobanov.cloudnotes.data.notesRemoteRepository
import kotlinx.coroutines.launch

class NotesMainViewModel: ViewModel() {

//    private val notes = database.getAllNotes()
//
//    init {
//        val notes = database.getAllNotes()
//    }
//
//    fun observeViewState(): LiveData<List<NoteEntity>> = notes
//
//    fun clear() {
//        viewModelScope.launch {
//            database.clear()
//        }
//    }

    fun observeViewState(): LiveData<ViewState> = notesRemoteRepository.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }
}