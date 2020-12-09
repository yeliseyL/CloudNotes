package com.eliseylobanov.cloudnotes.data

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.coroutines.flow.Flow

interface NotesRepository {
    fun observeNotes(): Flow<List<Note>>
    suspend fun addOrReplaceNote(newNote: Note)
    suspend fun clear()
    suspend fun delete(id: Long)
    suspend fun getCurrentUser(): User?
}