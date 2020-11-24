package com.eliseylobanov.cloudnotes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.ColorsRepository

class ColorChooserViewModel: ViewModel() {

    private val _colorList = MutableLiveData<List<Color>>()
    val colorList: LiveData<List<Color>>
        get() = _colorList

    init {
        _colorList.value = ColorsRepository.getColors()
    }

}