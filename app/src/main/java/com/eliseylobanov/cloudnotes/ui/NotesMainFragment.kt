package com.eliseylobanov.cloudnotes.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.databinding.NotesMainFragmentBinding
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModel
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModelFactory
import com.eliseylobanov.cloudnotes.viewmodels.ViewState
import kotlinx.android.synthetic.main.item_note.*
import kotlinx.android.synthetic.main.notes_main_fragment.*

class NotesMainFragment : Fragment(R.layout.notes_main_fragment) {

    private lateinit var dataSource: NoteDao

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this, NotesMainViewModelFactory(dataSource)).get(
            NotesMainViewModel::class.java
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: NotesMainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.notes_main_fragment, container, false)

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

        val adapter = NotesAdapter()


        viewModel.observeViewState().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        recyclerMain.adapter = adapter

        setHasOptionsMenu(true)
        fab.setOnClickListener {
            val note = Note()
            view.findNavController()
                .navigate(NotesMainFragmentDirections.actionNotesMainFragmentToNoteFragment(note)) }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_main, menu)
    }
}