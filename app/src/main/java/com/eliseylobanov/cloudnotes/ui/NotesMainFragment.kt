package com.eliseylobanov.cloudnotes.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModel
import com.eliseylobanov.cloudnotes.viewmodels.ViewState
import kotlinx.android.synthetic.main.item_note.*
import kotlinx.android.synthetic.main.notes_main_fragment.*

class NotesMainFragment : Fragment(R.layout.notes_main_fragment) {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
            NotesMainViewModel::class.java
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NotesAdapter()

        recyclerMain.adapter = adapter

        viewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Value -> {
                    adapter.submitList(it.notes)
                }
                ViewState.EMPTY -> Unit
            }
        }

        setHasOptionsMenu(true)
        fab.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_notesMainFragment_to_noteFragment))
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }
}