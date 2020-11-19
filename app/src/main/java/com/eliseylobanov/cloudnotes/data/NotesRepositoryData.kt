package com.eliseylobanov.cloudnotes.data

object NotesRepositoryData : NotesRepository {

    private val notes: List<Note> = listOf(
        Note(
            title = "My first note",
            note = "Kotlin is the best language ever",
            color = 0xfff06292.toInt()
        ),
        Note(
            title = "My first note",
            note = "Kotlin is the best language ever",
            color = 0xff9575cd.toInt()
        ),
        Note(
            title = "My first note",
            note = "Kotlin is the best language ever",
            color = 0xff64b5f6.toInt()
        ),
        Note(
            title = "My first note",
            note = "Kotlin is the best language ever",
            color = 0xff4db6ac.toInt()
        ),
        Note(
            title = "My first note",
            note = "Kotlin is the best language ever",
            color = 0xffb2ff59.toInt()
        ),
        Note(
            title = "My first note",
            note = "Kotlin is the best language ever",
            color = 0xffffeb3b.toInt()
        ),
        Note(
            title = "My first note",
            note = "Kotlin is the best language ever",
            color = 0xffff6e40.toInt()
        )
    )

    override fun getAllNotes(): List<Note> {
        return notes
    }
}