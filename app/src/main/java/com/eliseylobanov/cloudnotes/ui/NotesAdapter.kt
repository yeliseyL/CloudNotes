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


val DIFF_UTIL: DiffUtil.ItemCallback<Note> = object : DiffUtil.ItemCallback<Note>() {
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return true
    }
}
class NotesAdapter(val noteHandler: (Note) -> Unit) : ListAdapter<Note, NotesAdapter.NoteViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(parent)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class NoteViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    ) {

        private lateinit var currentNote: Note

        private val clickListener: View.OnClickListener = View.OnClickListener {
            noteHandler(currentNote)
        }

        fun bind(item: Note) {
            currentNote = item
            with(itemView) {
                date.text = item.noteDate
                title.text = item.titleText
                body.text = item.noteText
                cardView.setCardBackgroundColor(item.noteColor)
                setOnClickListener(clickListener)
            }
        }
    }
}
