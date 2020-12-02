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
import com.eliseylobanov.cloudnotes.viewmodels.*
import kotlinx.android.synthetic.main.fragment_color_chooser_dialog_list_dialog.*
import kotlinx.android.synthetic.main.notes_main_fragment.*
import kotlinx.android.synthetic.main.notes_main_fragment.recyclerMain

class ColorChooserDialogFragment : BottomSheetDialogFragment() {

    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
        ViewModelProvider(this).get(
            ColorChooserViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { return inflater.inflate(
            R.layout.fragment_color_chooser_dialog_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NoteColorAdapter {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("color", it)
        }

        viewModel.colorList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
        colorRecycler.adapter = adapter

    }
}