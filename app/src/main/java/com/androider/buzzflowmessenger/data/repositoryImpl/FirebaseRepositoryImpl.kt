package com.androider.buzzflowmessenger.data.repositoryImpl

import com.androider.buzzflowmessenger.data.mapper.MainMapper
import com.androider.buzzflowmessenger.data.models.AuthResultDTO
import com.androider.buzzflowmessenger.data.models.CurrentUserFirebase
import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
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

    private lateinit var authResultDTO: AuthResultDTO

    override fun signUp(
        name: String,
        email: String,
        password: String,
        callback: (AuthResultEntity) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                authResultDTO = AuthResultDTO(success = false, errorMessage = "Неизвестная ошибка")
                if (it.isSuccessful) {
                    val currentUser = it.result.user
                    if (currentUser != null) {

                        authResultDTO = AuthResultDTO(
                            success = true,
                            user = CurrentUserFirebase(
                                id = currentUser.uid,
                                email = currentUser.email,
                                isEmailVerified = currentUser.isEmailVerified,
                                displayName = currentUser.displayName,
                                photoUrl = currentUser.photoUrl,
                                providerId = currentUser.providerId,
                                phoneNumber = currentUser.phoneNumber,
                            )
                        )

                        val user = hashMapOf(
                            "id" to currentUser.uid,
                            "email" to currentUser.email,
                            "status" to "default",
                            "imageUrl" to "empty",
                        )

                        firestore.collection("Users")
                            .document(currentUser.uid)
                            .set(user)


                    }
                } else {
                    val errorMessage = when (it.exception) {
                        is FirebaseAuthWeakPasswordException -> "Пароль слишком слабый"
                        is FirebaseAuthInvalidCredentialsException -> "Неверный формат email"
                        is FirebaseAuthUserCollisionException -> "Этот email уже используется"
                        else -> "Неизвестная ошибка: ${it.exception?.message}"
                    }
                    authResultDTO = AuthResultDTO(
                        success = false,
                        errorMessage = errorMessage
                    )
                }
                callback(mainMapper.mapAuthResultDTOToEntity(authResultDTO))
            }

    }

    private fun createAuthResultFromException(exception: Exception?): AuthResultDTO {
        return when (exception) {
            is FirebaseAuthWeakPasswordException -> AuthResultDTO(
                success = false,
                errorMessage = "Пароль слишком слабый"
            )
            is FirebaseAuthInvalidCredentialsException -> AuthResultDTO(
                success = false,
                errorMessage = "Неверный формат email"
            )
            is FirebaseAuthUserCollisionException -> AuthResultDTO(
                success = false,
                errorMessage = "Этот email уже используется"
            )
            is FirebaseAuthException -> {
                when (exception.errorCode) {
                    "ERROR_OPERATION_NOT_ALLOWED" -> AuthResultDTO(
                        success = false,
                        errorMessage = "Регистрация пользователей сейчас не разрешена"
                    )
                    else -> AuthResultDTO(
                        success = false,
                        errorMessage = "Неизвестная ошибка: ${exception.localizedMessage}"
                    )
                }
            }
            else -> AuthResultDTO(
                success = false,
                errorMessage = "Аутентификация не удалась: ${exception?.message}"
            )
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

    override fun signOut(callback: (AuthResultEntity) -> Unit) {
        firebaseAuth.signOut()
        authResultDTO = AuthResultDTO(isSignedOut = true)
        callback(mainMapper.mapAuthResultDTOToEntity(authResultDTO))
    }



}