package com.eliseylobanov.cloudnotes.data

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.provider.FireStoreProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class NotesRemoteRepository(val provider: FireStoreProvider) : NotesRepository {

    override fun observeNotes(): Flow<List<Note>> {
        return provider.observeNotes()
    }

    override suspend fun addOrReplaceNote(newNote: Note) = withContext(Dispatchers.IO) {
        provider.addOrReplaceNote(newNote)
    }

    override suspend fun clear() {}

    override suspend fun getCurrentUser() = withContext(Dispatchers.IO) {
        provider.getCurrentUser()
    }
    override suspend fun delete(id: Long) = withContext(Dispatchers.IO) {
        provider.deleteNote(id)
    }

}

