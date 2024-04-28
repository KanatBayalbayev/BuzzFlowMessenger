package com.androider.buzzflowmessenger.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androider.buzzflowmessenger.domain.usecases.FindUserUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val findUserUseCase: FindUserUseCase

) : ViewModel() {
    private val _state = MutableLiveData<MainState>()
    val state: LiveData<MainState> get() = _state


    fun findUser(email: String) {
        _state.value = MainState.Loading
        findUserUseCase(email){
            if (it.success){
                _state.value = MainState.Success(it.foundUser)
            } else {
                _state.value = MainState.Error(it.errorMessage)
            }
        }
    }

}




