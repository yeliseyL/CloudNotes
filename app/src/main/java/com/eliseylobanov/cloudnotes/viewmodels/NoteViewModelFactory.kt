package com.eliseylobanov.cloudnotes.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity

class NoteViewModelFactory(
    private val note: Note,
    private val dataSource: NoteDao,
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(note, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}