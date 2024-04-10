package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject

class ResetPasswordUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(email: String){
        firebaseRepository.resetPassword(email)
    }

}