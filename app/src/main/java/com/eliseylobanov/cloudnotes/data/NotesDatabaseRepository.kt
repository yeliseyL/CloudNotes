package com.eliseylobanov.cloudnotes.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModel

class NotesDatabaseRepository(context: Context) : NotesRepository {
    private val dataSource = NoteDatabase.getInstance(context).noteDao

    override fun observeNotes(): LiveData<List<Note>> {
        val noteList =  MutableLiveData<List<Note>>()
        noteList.value = dataSource.getAllNotes().value?.map {
            it.toNote
        }
        return noteList
    }

    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
        return provider.addOrReplaceNote(newNote)
    }

    suspend fun getNoteById(id: Long): Note? {
        return dataSource.get(id)?.toNote
    }

}

internal val NoteEntity.toNote: Note
    get() = Note(
        this.noteId,
        this.titleText,
        this.noteText,
        this.noteDate,
        this.noteColor,
    )




