package com.eliseylobanov.cloudnotes

import android.provider.Settings.Global.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    private val _textData = MutableLiveData<String>()
    val testData: LiveData<String>
        get() = _textData

    init {
        _textData.value = "Hello"
    }

    fun changeText() {
        _textData.value = "Kotlin"
    }
}