package com.eliseylobanov.cloudnotes.data.provider

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.User

interface RemoteDataProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note)
    fun deleteNote(id: Long)
}