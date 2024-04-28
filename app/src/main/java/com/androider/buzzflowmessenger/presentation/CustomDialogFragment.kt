package com.androider.buzzflowmessenger.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.androider.buzzflowmessenger.R

class CustomDialogFragment : DialogFragment() {

    interface DialogListener {
        fun onConfirm()
        fun onCancel()
    }

    private var _listener: DialogListener? = null

    fun setDialogListener(listener: DialogListener) {
        _listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireContext())
        val view = layoutInflater.inflate(R.layout.custom_dialog, null)

        builder.setView(view)

        val btnCancel = view.findViewById<Button>(R.id.btn_cancel)
        val btnConfirm = view.findViewById<Button>(R.id.btn_confirm)

        btnCancel.setOnClickListener {
            _listener?.onCancel()
            dismiss()
        }

        btnConfirm.setOnClickListener {
            _listener?.onConfirm()
            dismiss()
        }

        return builder.create()
    }

    companion object {
        fun showDialog(fragmentManager: FragmentManager, listener: DialogListener) {
            val dialog = CustomDialogFragment().apply {
                setDialogListener(listener)
            }
            dialog.show(fragmentManager, "CustomDialog")
        }
    }
}