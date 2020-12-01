package com.eliseylobanov.cloudnotes.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.eliseylobanov.cloudnotes.data.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(private val notesRemoteRepository: NotesRepository, private var note: Note?) : ViewModel() {


    private val showErrorLiveData = MutableLiveData<Boolean>()
    private val lifecycleOwner: LifecycleOwner = LifecycleOwner { viewModelLifecycle }

    private val viewModelLifecycle = LifecycleRegistry(lifecycleOwner).also {
        it.currentState = Lifecycle.State.RESUMED
    }

    var noteDate = MutableLiveData<String>()
    var titleText = MutableLiveData<String>()
    var noteText = MutableLiveData<String>()
    var noteColor = MutableLiveData<Int>()

    init {
        noteDate.value = getDate()
        if (note == null) {
            titleText.value = ""
            noteText.value = ""
            noteColor.value = 0xffffffff.toInt()
        } else {
            note?.let {
                    titleText.value = it.titleText
                    noteText.value = it.noteText
                    noteColor.value = it.noteColor
                }
            }
        }

    private fun updateFields(note: Note) {
        note.noteDate = getDate()
        note.titleText = titleText.value.toString()
        note.noteText = noteText.value.toString()
//        note.noteColor = noteColor.value!!
    }

    fun createNote() {
//        viewModelScope.launch {
//            if (note == null) {
//                val newNote = NoteEntity()
//                updateFields(newNote)
//                database.insert(newNote)
//            } else {
//                updateFields(note!!)
//                database.update(note!!)
//            }
//        }
        val newNote = Note()
        updateFields(newNote)
        newNote.let { note ->
            notesRemoteRepository.addOrReplaceNote(note)
        }
    }

    fun showError(): LiveData<Boolean> = showErrorLiveData

    private fun getDate(): String {
        @SuppressLint("SimpleDateFormat")
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val cal = Calendar.getInstance()
        val now =  cal.time
        return sdf.format(now)
    }

}




