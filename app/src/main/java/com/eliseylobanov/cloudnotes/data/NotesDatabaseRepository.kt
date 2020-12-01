package com.eliseylobanov.cloudnotes.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.data.database.NoteEntity

class NotesDatabaseRepository(context: Context) : NotesRepository {
    private val dataSource = NoteDatabase.getInstance(context).noteDao

    override fun observeNotes(): LiveData<List<Note>> {
        val temp =  MutableLiveData<List<Note>>()
        temp.value = dataSource.getAllNotes().value?.map {
            it -> it.toNote
        }
        return temp
    }

    override fun addOrReplaceNote(newNote: Note) {
        dataSource.insert(newNote.toNoteEntity)
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

internal val Note.toNoteEntity: NoteEntity
    get() {
        val noteEntity = NoteEntity()
        noteEntity.noteId = this.noteId
        noteEntity.noteDate = this.noteDate
        noteEntity.titleText = this.titleText
        noteEntity.noteText = this.noteText
        return noteEntity
    }





