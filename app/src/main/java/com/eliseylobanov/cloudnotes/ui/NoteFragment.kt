package com.eliseylobanov.cloudnotes.ui

import android.app.Application
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.ColorsRepository
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.data.mapToColor
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModel
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModelFactory
import kotlinx.android.synthetic.main.item_note.*
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

        setHasOptionsMenu(true)

        val args = NoteFragmentArgs.fromBundle(requireArguments())
        val application: Application = requireNotNull(this.activity).application
        dataSource = NoteDatabase.getInstance(application).noteDao

        val viewModelFactory = NoteViewModelFactory(args.noteId, dataSource)

        val viewModel = ViewModelProvider(
            this, viewModelFactory
        ).get(NoteViewModel::class.java)

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
            viewModel.noteColor.value = (view.background as ColorDrawable).color
            viewModel.createNote()
            view.findNavController()
                .navigate(NoteFragmentDirections.actionNoteFragmentToNotesMainFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_note, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_color_select -> {
                this.findNavController()
                    .navigate(NoteFragmentDirections.actionNoteFragmentToColorChooserDialogFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        ColorsRepository.newColor.observe(viewLifecycleOwner, { newColor ->
            view?.setBackgroundColor(newColor.mapToColor(requireActivity()))
        })
    }


}