package com.eliseylobanov.cloudnotes.ui

import android.app.Application
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.ColorsRepository
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.databinding.ColorChooserDialogFragmentBinding
import com.eliseylobanov.cloudnotes.databinding.NoteFragmentBinding
import com.eliseylobanov.cloudnotes.viewmodels.*
import kotlinx.android.synthetic.main.notes_main_fragment.*
import kotlinx.android.synthetic.main.notes_main_fragment.recyclerMain
import org.koin.androidx.viewmodel.ext.android.viewModel

class ColorChooserDialogFragment : BottomSheetDialogFragment() {

    lateinit var binding: ColorChooserDialogFragmentBinding

    private val viewModel by viewModel<ColorChooserViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ColorChooserDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteColorAdapter {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("color", it)
        }

        viewModel.colorList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        binding.colorRecycler.adapter = adapter

    }
}