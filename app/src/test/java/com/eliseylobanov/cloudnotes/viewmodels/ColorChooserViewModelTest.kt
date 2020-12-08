package com.eliseylobanov.cloudnotes.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.ColorsRepository
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.NotesRepository
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import junit.framework.Assert
import junit.framework.Assert.assertEquals
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class ColorChooserViewModelTest {

    private val colorsRepositoryMock = mockk<ColorsRepository>()
    private lateinit var viewModel: ColorChooserViewModel


    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {}

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `should return Colors`() {
        var result: List<Color>? = null
        val testData = listOf(Color.BLUE, Color.GREEN)
        every { colorsRepositoryMock.getColors() } returns testData
        viewModel = ColorChooserViewModel(colorsRepositoryMock)
        viewModel.colorList.observeForever {
            result = it
        }
        assertEquals(testData, result)
    }
}