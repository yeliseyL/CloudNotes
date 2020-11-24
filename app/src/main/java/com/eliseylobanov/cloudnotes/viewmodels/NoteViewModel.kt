package com.eliseylobanov.cloudnotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.ColorsRepository
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.coroutines.launch

class NoteViewModel(private val noteId: Long?, private val database: NoteDao) : ViewModel() {

    var noteDate = MutableLiveData<String>()
    var titleText = MutableLiveData<String>()
    var noteText = MutableLiveData<String>()
    var noteColor = MutableLiveData<Int>()

    private var note: NoteEntity? = null

    init {

        if (noteId == null) {
            titleText.value = ""
            noteText.value = ""
            noteColor.value = 0xffffffff.toInt()
        } else {
            viewModelScope.launch {
                note = database.get(noteId)!!
                titleText.value = note!!.titleText
                noteText.value = note!!.noteText
                noteColor.value = note!!.noteColor
            }
        }
    }

    private fun updateFields(note: NoteEntity) {
//        note.noteDate = noteDate.value.toString()
        note.titleText = titleText.value.toString()
        note.noteText = noteText.value.toString()
        note.noteColor = noteColor.value!!
    }

    fun createNote() {
        viewModelScope.launch {
            if (note == null) {
                val newNote = NoteEntity()
                updateFields(newNote)
                database.insert(newNote)
            } else {
                updateFields(note!!)
                database.update(note!!)
            }
        }
    }
}




