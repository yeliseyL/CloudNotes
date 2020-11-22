package com.eliseylobanov.cloudnotes.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity

class NotesMainViewModel(
    private val database: NoteDao,
) : ViewModel() {

    private val notes = database.getAllNotes()

    init {
        val notes = database.getAllNotes()
    }

    fun observeViewState(): LiveData<List<NoteEntity>> = notes
}