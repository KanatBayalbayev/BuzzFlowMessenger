package com.androider.buzzflowmessenger.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androider.buzzflowmessenger.domain.usecases.IsLoggedInUseCase
import com.androider.buzzflowmessenger.domain.usecases.ResetPasswordUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignInUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignUpUseCase
import com.androider.buzzflowmessenger.presentation.utils.FunctionUtils
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase,
    private val isLoggedInUseCase: IsLoggedInUseCase
) : ViewModel() {
    private val _state = MutableLiveData<AuthState>()
    val state: LiveData<AuthState> get() = _state


    fun signIn(email: String, password: String) = signInUseCase(email, password)
    fun signUp(name: String, email: String, password: String)  {
        _state.value = AuthState.Loading
        signUpUseCase(name,email,password){
            Log.d("AuthViewModel", "callbackRes: $it")
            _state.value = it
        }

    }
    fun resetPassword(email: String) = resetPasswordUseCase(email)
    fun isLoggedIn() = isLoggedInUseCase()


}




