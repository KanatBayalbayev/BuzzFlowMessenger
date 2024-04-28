package com.androider.buzzflowmessenger.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androider.buzzflowmessenger.domain.usecases.IsLoggedInUseCase
import com.androider.buzzflowmessenger.domain.usecases.ResetPasswordUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignInUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignOutUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignUpUseCase
import com.androider.buzzflowmessenger.presentation.utils.FunctionUtils
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val signOutUseCase: SignOutUseCase,
) : ViewModel() {
    private val _state = MutableLiveData<AuthState>()
    val state: LiveData<AuthState> get() = _state

    fun signUp(name: String, email: String, password: String) {
        _state.value = AuthState.Loading
        signUpUseCase(name, email, password) {
            if (it.success) {
                _state.value = AuthState.Success(null)
            } else {
                _state.value = AuthState.Error(it.errorMessage)
            }
        }
    }
    fun signIn(email: String, password: String) {
        _state.value = AuthState.Loading
        signInUseCase(email, password){
            if (it.success){
                _state.value = AuthState.isSignedIn
            } else {
                _state.value = AuthState.Error(it.errorMessage)
            }
        }
    }


    fun resetPassword(email: String) = resetPasswordUseCase(email)
    fun isLoggedIn() {
        if (isLoggedInUseCase()){
            _state.value = AuthState.isSignedIn
        }
    }

    fun signOut(){
        _state.value = AuthState.Loading
        signOutUseCase(){
            if (it.isSignedOut){
                _state.value = AuthState.isSignedOut
            }
        }
    }


}




