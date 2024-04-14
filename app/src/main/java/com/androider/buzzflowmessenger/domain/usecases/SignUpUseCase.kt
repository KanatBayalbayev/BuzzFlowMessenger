package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class SignUpUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(
        name: String,
        email: String,
        password: String,
        callback: (AuthState) -> Unit
    ){
        firebaseRepository.signUp(name, email, password, callback)
    }


}