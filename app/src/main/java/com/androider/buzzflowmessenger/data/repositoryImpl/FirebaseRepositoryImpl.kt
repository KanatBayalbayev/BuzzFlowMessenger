package com.androider.buzzflowmessenger.data.repositoryImpl

import com.androider.buzzflowmessenger.data.mapper.MainMapper
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val mainMapper: MainMapper,
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : FirebaseRepository {

    override fun signUp(
        name: String,
        email: String,
        password: String,
        callback: (AuthState) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val currentUser = it.result.user
                    if (currentUser != null) {
                        callback(AuthState.Success(currentUser))

//                        val hashMapCurrentUser = hashMapOf(
//                            "id" to currentUser.uid,
//                            "email" to currentUser.email,
//                            "status" to ""
//                        )

                    }
                } else {
                    when (it.exception) {
                        is FirebaseAuthWeakPasswordException ->
                            callback(AuthState.Error("Пароль слишком слабый"))

                        is FirebaseAuthInvalidCredentialsException ->
                            callback(AuthState.Error("Неверный формат email"))

                        is FirebaseAuthUserCollisionException ->
                            callback(AuthState.Error("Этот email уже используется"))

                        is FirebaseAuthException -> {
                            when ((it.exception as FirebaseAuthException).errorCode) {
                                "ERROR_OPERATION_NOT_ALLOWED" ->
                                    callback(AuthState.Error("Регистрация пользователей сейчас не разрешена"))

                                else ->
                                    callback(
                                        AuthState.Error(
                                            "Неизвестная ошибка: ${
                                                (
                                                        it.exception as FirebaseAuthException).localizedMessage
                                            }"
                                        )
                                    )
                            }
                        }
                    }
                }
            }
    }

    override fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
    }

    override fun resetPassword(email: String) {
        firebaseAuth.sendPasswordResetEmail(email)
    }

    override fun isLoggedIn(): Boolean {
        return firebaseAuth.currentUser != null
    }


}