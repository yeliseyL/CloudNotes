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
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class NotesMainViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: NotesMainViewModel

    private var _resultLiveData = MutableLiveData<List<Note>>()
    private val resultLiveData: LiveData<List<Note>> get() = _resultLiveData


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.observeNotes() } returns resultLiveData
        viewModel = NotesMainViewModel(notesRepositoryMock)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }


    @Test
    fun `should return Notes`() {
        var result: List<Note>? = null
        val testData = listOf(Note(noteId = 1), Note(noteId = 2))
        viewModel.observeViewState().observeForever {
            result = when (it) {
                is ViewState.Value -> it.notes
                ViewState.EMPTY -> null
            }
        }
        _resultLiveData.value = testData
        assertEquals(testData, result)
    }

}