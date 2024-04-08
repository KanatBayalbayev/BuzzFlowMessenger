package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject


class SignUpUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(email: String, password: String) {
        firebaseRepository.signUp(email, password)
    }

}