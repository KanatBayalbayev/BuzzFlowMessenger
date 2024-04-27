package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class SignOutUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(callback: (AuthResultEntity) -> Unit){
         firebaseRepository.signOut(callback)
    }


}