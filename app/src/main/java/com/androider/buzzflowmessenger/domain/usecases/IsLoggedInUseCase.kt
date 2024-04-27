package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject

class IsLoggedInUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(): Boolean {
       return firebaseRepository.isLoggedIn()
    }

}