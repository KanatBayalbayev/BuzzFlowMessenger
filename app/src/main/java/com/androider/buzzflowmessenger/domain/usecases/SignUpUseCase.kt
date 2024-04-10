package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject


class SignUpUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(name: String, email: String, password: String): String {
       return firebaseRepository.signUp(name, email, password)
    }

}