package com.eliseylobanov.cloudnotes.data

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.ui.NoteFragment

enum class Color {
    WHITE,
    YELLOW,
    GREEN,
    BLUE,
    RED,
    VIOLET,
    PINK
}

fun Color.mapToColor(context: Context): Int {

    val id = when (this) {
        Color.WHITE -> R.color.color_white
        Color.YELLOW -> R.color.color_yellow
        Color.GREEN -> R.color.color_green
        Color.BLUE -> R.color.color_blue
        Color.RED -> R.color.color_red
        Color.VIOLET -> R.color.color_violet
        Color.PINK -> R.color.color_pink
    }

    return ContextCompat.getColor(context, id)
}

object ColorsRepository  {

    val newColor = MutableLiveData<Color>()

    private val colors: List<Color> = listOf(
        Color.BLUE,
        Color.GREEN,
        Color.PINK,
        Color.RED,
        Color.VIOLET,
        Color.WHITE,
        Color.YELLOW
    )

    fun getColors(): List<Color> {
        return colors
    }
}

