package com.eliseylobanov.cloudnotes.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.NotesDatabaseRepository
import com.eliseylobanov.cloudnotes.data.NotesRepository
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.coroutines.launch

class NotesMainViewModel(private val notesRepository: NotesRepository): ViewModel() {

    fun observeViewState(): LiveData<ViewState> = notesRepository.observeNotes()
        .map {
            if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
        }

    fun clear() {
        notesRepository.clear()
    }
}