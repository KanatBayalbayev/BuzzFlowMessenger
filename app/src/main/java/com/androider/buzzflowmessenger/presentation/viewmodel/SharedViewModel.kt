package com.androider.buzzflowmessenger.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {

    private val _isSpecialMode = MutableLiveData<Boolean>(false)
    val isSpecialMode: LiveData<Boolean> = _isSpecialMode

    fun setSpecialMode(value: Boolean) {
        _isSpecialMode.value = value
    }
}