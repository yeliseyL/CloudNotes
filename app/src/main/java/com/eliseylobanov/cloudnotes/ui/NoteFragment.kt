package com.eliseylobanov.cloudnotes.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModel
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModelFactory
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModel
import com.eliseylobanov.cloudnotes.viewmodels.NotesMainViewModelFactory
import kotlinx.android.synthetic.main.note_fragment.*

class NoteFragment : Fragment() {
    private lateinit var viewModel: NoteViewModel
    private lateinit var dataSource: NoteDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.note_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = NoteFragmentArgs.fromBundle(requireArguments())
        val application: Application = requireNotNull(this.activity).application
        dataSource = NoteDatabase.getInstance(application).noteDao

        val viewModelFactory = NoteViewModelFactory(args.note, dataSource)

        val viewModel = ViewModelProvider(
            this, viewModelFactory).get(NoteViewModel::class.java)

        viewModel.titleText.observe(viewLifecycleOwner, { text ->
            titleEditText.setText(text)
        })

        viewModel.noteText.observe(viewLifecycleOwner, { text ->
            noteEditText.setText(text)
        })

        viewModel.noteColor.observe(viewLifecycleOwner, { color ->
            view.setBackgroundColor(color)
        })

        noteFab.setOnClickListener {
            viewModel.titleText.value = titleEditText.text.toString()
            viewModel.noteText.value = noteEditText.text.toString()
            viewModel.createNote()
            view.findNavController()
                .navigate(NoteFragmentDirections.actionNoteFragmentToNotesMainFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_note, menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.action_color_select -> {
//
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}