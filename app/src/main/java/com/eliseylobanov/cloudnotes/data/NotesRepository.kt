package com.eliseylobanov.cloudnotes.data

import androidx.lifecycle.LiveData
import com.eliseylobanov.cloudnotes.data.database.NoteEntity

interface NotesRepository {
    fun observeNotes(): LiveData<List<Note>>
    fun addOrReplaceNote(newNote: Note)
    fun clear()
}