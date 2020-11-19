package com.eliseylobanov.cloudnotes.viewmodels

import com.eliseylobanov.cloudnotes.data.Note

sealed class ViewState {
    data class Value(val notes: List<Note>) : ViewState()
    object EMPTY : ViewState()
}