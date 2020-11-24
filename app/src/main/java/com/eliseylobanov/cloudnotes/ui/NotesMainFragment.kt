package com.eliseylobanov.cloudnotes.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.data.mapToColor
import com.eliseylobanov.cloudnotes.databinding.NotesMainFragmentBinding
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModel
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModelFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.notes_main_fragment.*

class NotesMainFragment : Fragment(R.layout.notes_main_fragment) {

    private lateinit var dataSource: NoteDao

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, NotesMainViewModelFactory(dataSource)).get(
            NotesMainViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: NotesMainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.notes_main_fragment, container, false
        )

        val application: Application = requireNotNull(this.activity).application
        dataSource = NoteDatabase.getInstance(application).noteDao
//        val viewModelFactory = NotesMainViewModelFactory(dataSource)
//
//        val viewModel = ViewModelProvider(
//                this, viewModelFactory).get(NotesMainViewModel::class.java)
        binding.lifecycleOwner = this
        binding.notesMainViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NotesAdapter {
            navigateToNote(it.noteId)
        }


        viewModel.observeViewState().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerMain.adapter = adapter

        setHasOptionsMenu(true)
        fab.setOnClickListener {
            view.findNavController()
                .navigate(NotesMainFragmentDirections.actionNotesMainFragmentToNoteFragment(null))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                Snackbar.make(requireView(), "Are you sure?", Snackbar.LENGTH_LONG)
                    .setAction("OK") { viewModel.clear() }
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun navigateToNote(noteId: Long?) {
        view?.findNavController()
            ?.navigate(NotesMainFragmentDirections.actionNotesMainFragmentToNoteFragment(noteId))
    }

}