package com.waichee.noteapp02.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog.Builder
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.waichee.noteapp02.viewmodels.NoteListViewModel

class ConfirmDeleteDialogue: DialogFragment() {

    private val viewModel: NoteListViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProviders.of(this, NoteListViewModel.Factory(activity.application))
            .get(NoteListViewModel::class.java)
    }

    companion object {
        fun newInstance(): ConfirmDeleteDialogue {
            return ConfirmDeleteDialogue()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return Builder(context!!)
            .setTitle("Operation Success")
            .setMessage("All notes deleted.")
            .setNeutralButton("OK") { _: DialogInterface, _: Int ->
                viewModel.doneDisplayAlert()
            }
            .create()
    }
}