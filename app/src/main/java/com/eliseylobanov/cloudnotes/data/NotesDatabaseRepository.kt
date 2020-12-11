package com.eliseylobanov.cloudnotes.data

import android.app.Application
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull

class NotesDatabaseRepository(application: Application) : NotesRepository {
    private val dataSource = NoteDatabase.getInstance(application).noteDao

    override fun observeNotes(): Flow<List<Note>> {
        val temp = MutableStateFlow<List<Note>?>(null)
        with(CoroutineScope (Job() + Dispatchers.IO)) {
            launch {
                dataSource.getAllNotes().collect {
                    if (it.isNotEmpty()) {
                        temp.value = noteListConvert(it)
                    }
                }
            }

        }
        return temp.filterNotNull()
    }

    override suspend fun getCurrentUser(): User? {
        return null
    }

    override suspend fun delete(id: Long) {
        dataSource.delete(id)
    }

    override suspend fun addOrReplaceNote(newNote: Note) {
        dataSource.insert(newNote.toNoteEntity)
    }

    suspend fun getNoteById(id: Long): Note? {
        return dataSource.get(id)?.toNote
    }

    override suspend fun clear() {
        dataSource.clear()
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
        if (this.noteId != 0L) {
            noteEntity.noteId = this.noteId
        }
        noteEntity.noteDate = this.noteDate
        noteEntity.titleText = this.titleText
        noteEntity.noteText = this.noteText
        noteEntity.noteColor = this.noteColor
        return noteEntity
    }





