package com.eliseylobanov.cloudnotes.data.provider

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.User
import kotlinx.coroutines.flow.Flow

interface RemoteDataProvider {
    fun getCurrentUser(): User?
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun deleteNote(id: Long)
}