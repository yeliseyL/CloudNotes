package com.eliseylobanov.cloudnotes.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.eliseylobanov.cloudnotes.data.database.NoteDao

class NotesMainViewModelFactory(
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NotesMainViewModel::class.java)) {
            return NotesMainViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}