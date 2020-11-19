package com.eliseylobanov.cloudnotes.data

interface NotesRepository {
    fun getAllNotes(): List<Note>
}