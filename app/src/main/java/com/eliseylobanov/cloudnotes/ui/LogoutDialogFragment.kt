package com.eliseylobanov.cloudnotes.ui

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.eliseylobanov.cloudnotes.R

class LogoutDialogFragment : DialogFragment() {
    companion object {
        val TAG = LogoutDialogFragment::class.java.name + "TAG"
        fun createInstance() = LogoutDialogFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(getString(R.string.ok_text)) { _, _ ->   findNavController()
                .previousBackStackEntry?.savedStateHandle?.set("logout", true) }
            .setNegativeButton(R.string.logout_dialog_cancel) {_, _ -> dismiss() }
            .create()
}