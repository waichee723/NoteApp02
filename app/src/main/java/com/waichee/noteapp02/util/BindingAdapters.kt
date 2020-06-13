package com.waichee.noteapp02.util

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.waichee.noteapp02.domain.Note
import com.waichee.noteapp02.ui.NoteListAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Note>?) {
    val adapter = recyclerView.adapter as NoteListAdapter
    adapter.submitList(data)
}

@BindingAdapter("noteTitle")
fun bindNoteTitle(textView: TextView, title: String?) {
    title?.let {
        textView.text = title
    }
}
