package com.androider.buzzflowmessenger.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.androider.buzzflowmessenger.domain.usecases.LoginUseCase
import com.androider.buzzflowmessenger.domain.usecases.SignUpUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {


    fun login(email: String, password: String) = loginUseCase(email, password)

    fun signUp(email: String, password: String) = signUpUseCase(email, password)



}