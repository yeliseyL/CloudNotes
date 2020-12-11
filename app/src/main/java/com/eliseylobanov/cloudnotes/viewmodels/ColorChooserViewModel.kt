package com.eliseylobanov.cloudnotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.ColorsRepository
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.NotesRepository
import kotlinx.coroutines.launch

class ColorChooserViewModel(colorsRepository: ColorsRepository): ViewModel() {

    private val _colorList = MutableLiveData<List<Color>>()
    val colorList: LiveData<List<Color>>
        get() = _colorList

    init {
        _colorList.value = colorsRepository.getColors()

    }
}