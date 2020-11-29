//package com.eliseylobanov.cloudnotes.data
//
//import android.app.Application
//import android.content.Context
//import androidx.lifecycle.LiveData
//import androidx.multidex.MultiDexApplication
//import com.eliseylobanov.cloudnotes.data.database.NoteDao
//import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
//import com.eliseylobanov.cloudnotes.data.provider.FireStoreProvider
//
//class NotesDatabaseRepository(context: Context) : NotesRepository {
//    val dataSource = NoteDatabase.getInstance(context).noteDao
//
//    override fun observeNotes(): LiveData<List<Note>> {
//        return provider.observeNotes()
//    }
//
//    override fun addOrReplaceNote(newNote: Note): LiveData<Result<Note>> {
//        return provider.addOrReplaceNote(newNote)
//    }
//}
//
//
//
