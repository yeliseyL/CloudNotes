package com.eliseylobanov.cloudnotes.viewmodels

import android.annotation.SuppressLint
import androidx.lifecycle.*
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.ColorsRepository
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import com.eliseylobanov.cloudnotes.data.notesRemoteRepository
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(private val noteId: Long?) : ViewModel() {

    private val showErrorLiveData = MutableLiveData<Boolean>()

    private val lifecycleOwner: LifecycleOwner = LifecycleOwner { viewModelLifecycle }

    private val viewModelLifecycle = LifecycleRegistry(lifecycleOwner).also {
        it.currentState = Lifecycle.State.RESUMED
    }

    var noteDate = MutableLiveData<String>()
    var titleText = MutableLiveData<String>()
    var noteText = MutableLiveData<String>()
    var noteColor = MutableLiveData<Int>()

//    private var note: NoteEntity? = null

    init {

//        if (noteId == null) {
//            noteDate.value = getDate()
//            titleText.value = ""
//            noteText.value = ""
//            noteColor.value = 0xffffffff.toInt()
//        } else {
//            viewModelScope.launch {
//                note = database.get(noteId)!!
//                noteDate.value = getDate()
//                titleText.value = note!!.titleText
//                noteText.value = note!!.noteText
//                noteColor.value = note!!.noteColor
//            }
//        }
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
            notesRemoteRepository.addOrReplaceNote(note).observe(lifecycleOwner) {
                it.onFailure {
                    showErrorLiveData.value = true
                }
            }
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




