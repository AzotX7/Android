package com.example.homew1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import com.example.homew1.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private var callback: ((String) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_bottom_sheet, container, false)

        val editText = view.findViewById<EditText>(R.id.editTextBottomSheet)
        val buttonSave = view.findViewById<Button>(R.id.buttonSaveBottomSheet)

        editText.addTextChangedListener {
            buttonSave.isEnabled = it.toString().isNotEmpty()
        }

        buttonSave.setOnClickListener {
            callback?.invoke(editText.text.toString())
            dismiss()
        }

        return view
    }

    fun setCallback(callback: (String) -> Unit) {
        this.callback = callback
    }
}
