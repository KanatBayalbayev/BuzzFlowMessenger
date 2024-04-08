package com.androider.buzzflowmessenger.domain

import javax.inject.Inject

class Login @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    fun invoke(){
        firebaseRepository.hello()
    }

}