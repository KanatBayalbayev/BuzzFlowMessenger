package com.androider.buzzflowmessenger.presentation.utils

import android.widget.EditText


fun EditText.getTrimmedValue(): String{
    return this.text.toString().trim()
}