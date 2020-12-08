package com.eliseylobanov.cloudnotes.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.ColorsRepository
import com.eliseylobanov.cloudnotes.data.NotesRepository
import com.eliseylobanov.cloudnotes.data.User
import com.eliseylobanov.cloudnotes.errors.NoAuthException
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class SplashViewModelTest {

    private val notesRepositoryMock = mockk<NotesRepository>()
    private lateinit var viewModel: SplashViewModel


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { notesRepositoryMock.getCurrentUser() } returns null
        viewModel = SplashViewModel(notesRepositoryMock)
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should return error`() {
        var result: Any? = null
        viewModel.observeViewState().observeForever {
            result = (it as? SplashViewState.Error)?.error
        }
        assertTrue(result is NoAuthException)
    }

    @Test
    fun `should return auth`() {
        var result: Any? = null
        every { notesRepositoryMock.getCurrentUser() } returns User("name", "email")
        viewModel = SplashViewModel(notesRepositoryMock)
        viewModel.observeViewState().observeForever {
            result = it as? SplashViewState.Auth
        }
        assertEquals(result, SplashViewState.Auth)
    }

}