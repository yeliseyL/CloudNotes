package com.eliseylobanov.cloudnotes.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.eliseylobanov.cloudnotes.R
import com.eliseylobanov.cloudnotes.data.Note
import com.eliseylobanov.cloudnotes.data.database.NoteDao
import com.eliseylobanov.cloudnotes.data.database.NoteDatabase
import com.eliseylobanov.cloudnotes.data.database.NoteEntity
import kotlinx.android.synthetic.main.item_note.view.*


val DIFF_UTIL: DiffUtil.ItemCallback<NoteEntity> = object : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
        return true
    }
}
class NotesAdapter(val noteHandler: (NoteEntity) -> Unit) : ListAdapter<NoteEntity, NotesAdapter.NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    ) {

        private lateinit var currentNote: NoteEntity

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }

        fun bind(item: NoteEntity) {
            currentNote = item
            with(itemView) {
                title.text = item.titleText
                body.text = item.noteText
                cardView.setCardBackgroundColor(item.noteColor)
                setOnClickListener(clickListener)
            }
        }
    }
}
