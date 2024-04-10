package com.androider.buzzflowmessenger.domain.repository

interface FirebaseRepository {

    fun signUp(name: String, email: String, password: String): String
    fun signIn(email: String, password: String)
    fun resetPassword(email: String)


}