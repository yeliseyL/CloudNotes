package com.eliseylobanov.cloudnotes.ui

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.databinding.NotesMainFragmentBinding
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModel
import com.eliseylobanov.cloudnotes.viewmodels.ViewState
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.notes_main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesMainFragment : Fragment(R.layout.notes_main_fragment) {

    private val viewModel by viewModel<NotesMainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        val binding: NotesMainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.notes_main_fragment, container, false
        )
        binding.lifecycleOwner = this
        binding.notesMainViewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NotesAdapter {
            navigateToNote(it)
        }

        viewModel.observeViewState().observe(viewLifecycleOwner) {
            when (it) {
                is ViewState.Value -> {
                    adapter.submitList(it.notes)
                }
                ViewState.EMPTY -> Unit
            }
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

    private fun navigateToNote(note: Note?) {
        view?.findNavController()
            ?.navigate(NotesMainFragmentDirections.actionNotesMainFragmentToNoteFragment(note))
    }

}