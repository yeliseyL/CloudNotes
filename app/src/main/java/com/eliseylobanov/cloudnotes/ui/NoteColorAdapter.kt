package com.eliseylobanov.cloudnotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.Color
import com.eliseylobanov.cloudnotes.data.mapToColor
import com.eliseylobanov.cloudnotes.databinding.ItemColorBinding
import kotlinx.android.synthetic.main.item_color.view.*

val DIFF_UTIL_COLOR: DiffUtil.ItemCallback<Color> = object : DiffUtil.ItemCallback<Color>() {
    override fun areItemsTheSame(oldItem: Color, newItem: Color): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Color, newItem: Color): Boolean {
        return true
    }
}

class NoteColorAdapter(val colorHandler: (Color) -> Unit) : ListAdapter<Color, NoteColorAdapter.NoteColorViewHolder>(DIFF_UTIL_COLOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteColorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemColorBinding.inflate(inflater, parent, false)
        return NoteColorViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteColorViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteColorViewHolder(val binding: ItemColorBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {

        private lateinit var currentColor: Color

        private val clickListener: View.OnClickListener = View.OnClickListener {
            colorHandler(currentColor)
        }

        fun bind(item: Color) {
            currentColor = item
            with(itemView) {
                binding.colorBtn.setBackgroundColor(item.mapToColor(context))
                setOnClickListener(clickListener)
            }
        }
    }
}
