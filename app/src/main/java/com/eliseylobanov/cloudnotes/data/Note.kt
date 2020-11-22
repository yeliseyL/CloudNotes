package com.eliseylobanov.cloudnotes.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Note(
    val id: Long = 0L,
    val titleText: String = "",
    val noteText: String = "",
    val noteColor: Int = 0xffffffff.toInt(),
) : Parcelable
