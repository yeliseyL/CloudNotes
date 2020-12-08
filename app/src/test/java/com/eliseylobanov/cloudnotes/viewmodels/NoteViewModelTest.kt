package com.eliseylobanov.cloudnotes.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.NotesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NoteViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: NoteViewModel

    private var _resultLiveData = MutableLiveData<List<Note>>()
    private val resultLiveData: LiveData<List<Note>> get() = _resultLiveData


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.addOrReplaceNote(any()) } returns Unit
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }


    @Test
    fun `ViewModel Note text changed`() {
        val currentNote = Note(noteText = "Note text")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.noteText.value = "New text"
        viewModel.updateFields(currentNote)
        assertEquals("New text", viewModel.note?.noteText)
    }

    @Test
    fun `ViewModel Note title changed`() {
        val currentNote = Note(titleText = "Note title")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.titleText.value = "New title"
        viewModel.updateFields(currentNote)
        assertEquals("New title", viewModel.note?.titleText)
    }

    @Test
    fun `Error LiveData contains nothing after save`() {
        val currentNote = Note(titleText = "Note title")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)
        viewModel.createNote()
        assertTrue(viewModel.showError().value == null)
    }

    @Test
    fun `NotesRepository addOrReplaceNote called with correct title Note`() {
        val currentNote = Note(titleText = "Note title")
        viewModel = NoteViewModel(notesRepositoryMock, currentNote)

        viewModel.titleText.value = "New title"
        viewModel.updateFields(currentNote)
        viewModel.createNote()

        verify(exactly = 1) {
            notesRepositoryMock.addOrReplaceNote(match { it.titleText == "New title" })
        }

    }
}