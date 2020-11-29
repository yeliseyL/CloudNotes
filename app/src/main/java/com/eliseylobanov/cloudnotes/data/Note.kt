package com.eliseylobanov.cloudnotes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Note(
    var noteId: Long = 0L,
    var titleText: String = "",
    var noteText: String = "",
    var noteDate: String = "",
    var noteColor: Int = 0xffffffff.toInt(),
) : Parcelable
