package com.waichee.noteapp02.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.waichee.noteapp02.R
import com.waichee.noteapp02.databinding.FragmentNoteDetailBinding
import com.waichee.noteapp02.util.hideKeyboard
import com.waichee.noteapp02.viewmodels.NoteDetailViewModel

class NoteDetailFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val application = requireNotNull(activity).application
        val binding: FragmentNoteDetailBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_detail,
            container,
            false
        )
        binding.setLifecycleOwner(viewLifecycleOwner)

        val note = NoteDetailFragmentArgs.fromBundle(arguments!!).selectedNoteId
        val viewModel = ViewModelProviders.of(this, NoteDetailViewModel.Factory(note, application))
            .get(NoteDetailViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.navigateToNoteList.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(NoteDetailFragmentDirections.actionNoteDetailFragmentToNoteListFragment())
                viewModel.doneNavigateToNoteList()
                hideKeyboard()
            }
        })

        return binding.root
    }
}