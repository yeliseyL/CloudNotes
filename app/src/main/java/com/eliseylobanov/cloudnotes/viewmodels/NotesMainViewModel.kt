package com.eliseylobanov.cloudnotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eliseylobanov.cloudnotes.data.NotesRepositoryData

class NotesMainViewModel : ViewModel() {
    private val viewStateLiveData = MutableLiveData<ViewState>(ViewState.EMPTY)

    init {
        val notes = NotesRepositoryData.getAllNotes()
        viewStateLiveData.value = ViewState.Value(notes)
    }

    fun observeViewState(): LiveData<ViewState> = viewStateLiveData
}