package com.waichee.noteapp02.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waichee.noteapp02.databinding.NoteListItemBinding
import com.waichee.noteapp02.domain.Note
import com.waichee.noteapp02.generated.callback.OnClickListener

class NoteListAdapter(private val onClickListener: OnClickListener): ListAdapter<Note, NoteListAdapter.ViewHolder>(DiffCallback) {
    class ViewHolder(private var binding: NoteListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(note: Note) {
            binding.note = note
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Note>() {
        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem === newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(NoteListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(note.id)
        }
        holder.bind(note)
    }

    class OnClickListener(val clickListener: (noteId: Long) -> Unit) {
        fun onClick(noteId: Long) = clickListener(noteId)
    }
}