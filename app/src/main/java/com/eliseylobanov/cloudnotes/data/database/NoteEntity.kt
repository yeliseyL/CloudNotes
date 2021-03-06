package com.eliseylobanov.cloudnotes.data.database

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "notes_table")
class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    var noteId: Long = 0L

    @ColumnInfo(name = "note_date")
    var noteDate: String = ""

    @ColumnInfo(name = "title_text")
    var titleText: String = ""

    @ColumnInfo(name = "note_text")
    var noteText: String = ""

    @ColumnInfo(name = "note_color")
    var noteColor: Int = 0xffffffff.toInt()
}


