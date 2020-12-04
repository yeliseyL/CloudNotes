package com.eliseylobanov.cloudnotes.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NotesDatabaseRepository(application: Application) : NotesRepository {
    private val dataSource = NoteDatabase.getInstance(application).noteDao

    override fun observeNotes(): LiveData<List<Note>> {
        val temp =  MutableLiveData<List<Note>>()
        GlobalScope.launch {
            dataSource.getAllNotes().collect {
                if (it.isNotEmpty()) {
                    temp.postValue(noteListConvert(it))
                }
            }
        }
        return temp
    }

    override fun getCurrentUser(): User? {
        return null
    }

    override fun delete(id: Long) {
        GlobalScope.launch {
            dataSource.delete(id)
        }
    }

    override fun addOrReplaceNote(newNote: Note) {
        GlobalScope.launch {
            dataSource.insert(newNote.toNoteEntity)
        }
    }

    suspend fun getNoteById(id: Long): Note? {
        return dataSource.get(id)?.toNote
    }

    override fun clear() {
        GlobalScope.launch {
            dataSource.clear()
        }
    }
}

fun noteListConvert(list: List<NoteEntity>): List<Note> {
    return list.map { it.toNote }
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
        if (this.noteId != 0L) {noteEntity.noteId = this.noteId}
        noteEntity.noteDate = this.noteDate
        noteEntity.titleText = this.titleText
        noteEntity.noteText = this.noteText
        noteEntity.noteColor = this.noteColor
        return noteEntity
    }





