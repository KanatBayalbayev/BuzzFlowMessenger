package com.androider.buzzflowmessenger.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.androider.buzzflowmessenger.domain.usecases.ResetPasswordUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignInUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignUpUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {


    fun signIn(email: String, password: String) = signInUseCase(email, password)
    fun signUp(name: String, email: String, password: String) = signUpUseCase(name, email, password)
    fun resetPassword(email: String) = resetPasswordUseCase(email)


}