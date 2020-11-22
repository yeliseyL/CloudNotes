package com.eliseylobanov.cloudnotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.coroutines.launch

class NoteViewModel(private val note: Note, private val database: NoteDao) : ViewModel() {

    var noteDate = MutableLiveData<String>()
    var titleText = MutableLiveData<String>()
    var noteText = MutableLiveData<String>()
    var noteColor = MutableLiveData<Int>()

    init {
        titleText.value = note.titleText
        noteText.value = note.noteText
        noteColor.value = note.noteColor
        }

//    fun updateFields() {
//        note.noteDate = noteDate.value.toString()
//        note.titleText = titleText.value.toString()
//        note.noteText = noteText.value.toString()
//        note.noteColor = noteColor.value!!
//    }

    fun createNote() {
        viewModelScope.launch {
            if (database.get(note.id) == null) {
                val newNote = NoteEntity()
                newNote.titleText = titleText.value.toString()
                newNote.noteText = noteText.value.toString()
                newNote.noteColor = noteColor.value!!
                database.insert(newNote)
            }
        }
//        updateFields()
    }

    fun updateNote() {
//        updateFields()
//        database.update(note)
    }
}




