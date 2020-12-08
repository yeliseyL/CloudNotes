package com.eliseylobanov.cloudnotes.ui

import androidx.core.os.bundleOf
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModel
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.stopKoin

@RunWith(AndroidJUnit4::class)
class NoteFragmentTest {

    private val mockViewModel: NoteViewModel = mockk(relaxed = true)

    @Before
    fun setup() {
        kotlin.runCatching { startKoin { } }

        loadKoinModules(
            module {
                viewModel { (_: Note?) ->
                    mockViewModel
                }
            }
        )
    }

    @After
    fun clean() {
        stopKoin()
    }

    @Test
    fun save_note_on_button_click() {
        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())
        navController.setGraph(R.navigation.navigation)
        val fragmentArgs = bundleOf(Pair("note", null))
        val scenario = launchFragmentInContainer<NoteFragment>(fragmentArgs, R.style.Theme_AppCompat)
        scenario.onFragment{fragment ->
            Navigation.setViewNavController(fragment.requireView(), navController)}

        onView(withId(R.id.noteFab)).perform(click())

        verify { mockViewModel.createNote() }
    }

}