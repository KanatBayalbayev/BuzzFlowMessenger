package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(email: String, password: String, callback: (AuthResultEntity) -> Unit){
        firebaseRepository.signIn(email, password, callback)
    }

}