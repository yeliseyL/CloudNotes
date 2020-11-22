package com.eliseylobanov.cloudnotes.viewmodels

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.database.NoteEntity

sealed class ViewState {
    data class Value(val notes: LiveData<List<NoteEntity>>) : ViewState()
    object EMPTY : ViewState()
}