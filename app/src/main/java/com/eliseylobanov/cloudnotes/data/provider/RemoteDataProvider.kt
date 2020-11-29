package com.eliseylobanov.cloudnotes.data.provider

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.Note

interface RemoteDataProvider {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>>
}