package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(email: String, password: String){
        firebaseRepository.login(email, password)
    }

}