package com.androider.buzzflowmessenger.domain.repository

interface FirebaseRepository {

    fun signUp(email: String, password: String)
    fun login(email: String, password: String)


}