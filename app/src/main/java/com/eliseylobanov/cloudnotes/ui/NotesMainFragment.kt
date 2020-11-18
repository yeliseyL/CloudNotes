package com.eliseylobanov.cloudnotes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModel
import kotlinx.android.synthetic.main.notes_main_fragment.*

class NotesMainFragment : Fragment(R.layout.notes_main_fragment) {

    private lateinit var viewModel: NotesMainViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotesMainViewModel::class.java)

        fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_notesMainFragment_to_noteFragment))

    }

}