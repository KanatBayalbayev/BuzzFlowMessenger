package com.androider.buzzflowmessenger.data.repositoryImpl

import com.androider.buzzflowmessenger.data.mapper.MainMapper
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val mainMapper: MainMapper,
    private val firebaseAuth: FirebaseAuth
): FirebaseRepository {
    override fun signUp(name: String, email: String, password: String): String {
        var resultOfSignUp  = ""
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task ->
                if (task.isSuccessful){
                    resultOfSignUp = "Success"
                } else {
                    resultOfSignUp = task.exception?.message.toString()
                }
            }
        return  resultOfSignUp
    }

    override fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email,password)
    }

    override fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
    }


}