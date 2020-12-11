package com.eliseylobanov.cloudnotes.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.NotesDatabaseRepository
import com.eliseylobanov.cloudnotes.data.NotesRepository
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class NotesMainViewModel(private val notesRepository: NotesRepository): ViewModel() {

    private val notesLiveData = MutableLiveData<ViewState>()

    init {
        notesRepository.observeNotes()
            .onEach {
                notesLiveData.value = if (it.isEmpty()) ViewState.EMPTY else ViewState.Value(it)
            }
            .launchIn(viewModelScope)
    }

    fun observeViewState(): LiveData<ViewState> = notesLiveData

    fun clear() {
        viewModelScope.launch {
            notesRepository.clear()
        }
    }
}