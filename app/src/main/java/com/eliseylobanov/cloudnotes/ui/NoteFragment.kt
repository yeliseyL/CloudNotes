package com.eliseylobanov.cloudnotes.ui

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.viewmodels.NoteViewModel
import kotlinx.android.synthetic.main.note_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class NoteFragment : Fragment() {


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

        val viewModel by viewModel<NoteViewModel> {
            parametersOf(args.note)
        }

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
//            viewModel.noteColor.value = (view.background as ColorDrawable).color
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

//    override fun onResume() {
//        super.onResume()
//        ColorsRepository.newColor.observe(viewLifecycleOwner, { newColor ->
//            view?.setBackgroundColor(newColor.mapToColor(requireActivity()))
//        })
//    }


}