package com.eliseylobanov.cloudnotes.data

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.provider.FireStoreProvider

class NotesRemoteRepository (val provider: FireStoreProvider) : NotesRepository {

    override fun observeNotes(): LiveData<List<Note>> {
        return provider.observeNotes()
    }

    override fun addOrReplaceNote(newNote: Note) {
        return provider.addOrReplaceNote(newNote)
    }
}

