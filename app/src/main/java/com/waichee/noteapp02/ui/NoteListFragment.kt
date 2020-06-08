package com.waichee.noteapp02.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.waichee.noteapp02.R
import com.waichee.noteapp02.databinding.FragmentNoteListBinding
import com.waichee.noteapp02.viewmodels.NoteListViewModel

class NoteListFragment: Fragment() {
    private val viewModel: NoteListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, NoteListViewModel.Factory(activity.application))
            .get(NoteListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentNoteListBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_note_list,
            container,
            false
        )

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.noteRecyclerView.adapter = NoteListAdapter(NoteListAdapter.OnClickListener{
            viewModel.displayNoteDetail(it)
        })

        viewModel.navigateToNoteDetail.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                this.findNavController().navigate(NoteListFragmentDirections.actionNoteListFragmentToNoteDetailFragment(it))
                viewModel.doneNavigateToDetail()
            }
        })

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}