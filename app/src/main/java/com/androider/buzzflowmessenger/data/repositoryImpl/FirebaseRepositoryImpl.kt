package com.androider.buzzflowmessenger.data.repositoryImpl

import android.util.Log
import com.androider.buzzflowmessenger.data.mapper.MainMapper
import com.androider.buzzflowmessenger.data.models.AuthResultDTO
import com.androider.buzzflowmessenger.data.models.CurrentUserDTO
import com.androider.buzzflowmessenger.data.models.FoundUserDTO
import com.androider.buzzflowmessenger.data.models.MainResultDTO
import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.domain.models.MainResultEntity
import com.androider.buzzflowmessenger.domain.models.MessageEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val mainMapper: MainMapper,
    private val firebaseAuth: FirebaseAuth,
    private val database: FirebaseDatabase
) : FirebaseRepository {

    private lateinit var authResultDTO: AuthResultDTO
    private lateinit var mainResultDTO: MainResultDTO

    private val users = database.getReference("Users")
    private val chats = database.getReference("Friends")
    private val messages = database.getReference("Messages")

    override fun signUp(
        name: String,
        email: String,
        password: String,
        callback: (AuthResultEntity) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val currentUser = it.user
                if (currentUser != null) {
                    authResultDTO = AuthResultDTO(
                        success = true,
                        user = CurrentUserDTO(
                            id = currentUser.uid,
                            email = currentUser.email,
                            password = password,
                            name = name,
                            online = true
                        )
                    )
                    callback(mainMapper.mapAuthResultDTOToEntity(authResultDTO))

                    users.child(currentUser.uid).setValue(authResultDTO.user)

                }
            }
            .addOnFailureListener {
                val exception = createAuthResultFromException(it)
                callback(mainMapper.mapAuthResultDTOToEntity(exception))
            }
//            .addOnCompleteListener {
//                authResultDTO = AuthResultDTO(success = false, errorMessage = "Неизвестная ошибка")
//                if (it.isSuccessful) {
//                    val currentUser = it.result.user
//                    if (currentUser != null) {
//
//                        authResultDTO = AuthResultDTO(
//                            success = true,
//                            user = CurrentUserFirebase(
//                                id = currentUser.uid,
//                                email = currentUser.email,
//                                isEmailVerified = currentUser.isEmailVerified,
//                                displayName = currentUser.displayName,
//                                photoUrl = currentUser.photoUrl,
//                                providerId = currentUser.providerId,
//                                phoneNumber = currentUser.phoneNumber,
//                            )
//                        )
//
//                        val user = hashMapOf(
//                            "id" to currentUser.uid,
//                            "email" to currentUser.email,
//                            "status" to "default",
//                            "imageUrl" to "empty",
//                        )
//
//                        firestore.collection("Users")
//                            .document(currentUser.uid)
//                            .set(user)
//
//
//                    }
//                } else {
//                    val errorMessage = when (it.exception) {
//                        is FirebaseAuthWeakPasswordException -> "Пароль слишком слабый"
//                        is FirebaseAuthInvalidCredentialsException -> "Неверный формат email"
//                        is FirebaseAuthUserCollisionException -> "Этот email уже используется"
//                        else -> "Неизвестная ошибка: ${it.exception?.message}"
//                    }
//                    authResultDTO = AuthResultDTO(
//                        success = false,
//                        errorMessage = errorMessage
//                    )
//                }
//                callback(mainMapper.mapAuthResultDTOToEntity(authResultDTO))
//            }

    }


    override fun signIn(email: String, password: String, callback: (AuthResultEntity) -> Unit) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                val currentUser = it.user
                if (currentUser != null) {
                    authResultDTO = AuthResultDTO(
                        success = true,
                        user = CurrentUserDTO(
                            id = currentUser.uid,
                            email = currentUser.email,
                            password = password,
                            name = currentUser.displayName,
                            online = true
                        )

                    )
                    callback(mainMapper.mapAuthResultDTOToEntity(authResultDTO))
                }
            }
            .addOnFailureListener {
                val exception = createAuthResultFromException(it)
                callback(mainMapper.mapAuthResultDTOToEntity(exception))
            }
    }

    private fun createAuthResultFromException(exception: Exception?): AuthResultDTO {
        return when (exception) {
            is FirebaseAuthInvalidCredentialsException -> AuthResultDTO(
                success = false,
                errorMessage = "Сообщите пользователю о неправильном email или пароле"
            )

            is FirebaseAuthInvalidUserException -> AuthResultDTO(
                success = false,
                errorMessage = "Сообщите пользователю, что аккаунт не найден"
            )

            is FirebaseNetworkException -> AuthResultDTO(
                success = false,
                errorMessage = "Сообщите пользователю о проблемах с сетью"
            )

            else -> AuthResultDTO(
                success = false,
                errorMessage = "Аутентификация не удалась: ${exception?.message}"
            )
        }
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

    override fun findUser(email: String, callback: (MainResultEntity) -> Unit) {
        val currentUserEmail = firebaseAuth.currentUser?.email
        users.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (user in snapshot.children) {
                    val userEmail = user.child("email").getValue(String::class.java)
                    Log.d(TAG, "userEmail: $userEmail")
                    if (currentUserEmail == userEmail) {
                        continue
                    }
                    if (userEmail == email) {
                        val foundUser = user.getValue(FoundUserDTO::class.java)
                        Log.d(TAG, "foundUser: $foundUser")
                        if (foundUser != null) {
                            mainResultDTO = MainResultDTO(
                                success = true,
                                user = foundUser
                            )
                            Log.d(TAG, "mainResultDTO: $mainResultDTO")
                            callback(mainMapper.mapMainResultDTOToEntity(mainResultDTO))
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

    override fun addFoundUserToChats(foundUser: FoundUserEntity) {

        foundUser.id?.let {
            chats.child(firebaseAuth.currentUser?.uid ?: "").child(it).setValue(foundUser)
        }

    }

    override fun getChats(callback: (MainResultEntity) -> Unit) {
        chats.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val listOfFriends = arrayListOf<FoundUserEntity>()
                for (user in snapshot.children) {
                    if (user.key == firebaseAuth.currentUser?.uid) {
                        for (friend in user.children) {
                            val friendFromDB = friend.getValue(FoundUserEntity::class.java)

                            if (friendFromDB != null) {
                                Log.d(TAG, "onDataChange: $friendFromDB")
                                listOfFriends.add(friendFromDB)
                            }
                        }
                    }
                }
                mainResultDTO = MainResultDTO(
                    success = true,
                    chats = listOfFriends
                )
                callback(mainMapper.mapMainResultDTOToEntity(mainResultDTO))

            }

            override fun onCancelled(error: DatabaseError) {
                mainResultDTO = MainResultDTO(
                    success = false,
                    errorMessage = error.message
                )
                callback(mainMapper.mapMainResultDTOToEntity(mainResultDTO))
            }

        })
    }

    override fun sendMessage(
        messageEntity: MessageEntity,
        currentUserEntity: CurrentUserEntity
    ) {
        chats.child(messageEntity.companionID).child(messageEntity.senderID).setValue(currentUserEntity)
        chats
            .child(messageEntity.companionID)
            .child(messageEntity.senderID)
            .child("lastMessage")
            .setValue(messageEntity.textMessage)
        chats
            .child(messageEntity.companionID)
            .child(messageEntity.senderID)
            .child("lastTimeMessageSent")
            .setValue(messageEntity.timestamp)
        chats
            .child(messageEntity.senderID)
            .child(messageEntity.companionID)
            .child("lastMessage")
            .setValue(messageEntity.textMessage)
        chats
            .child(messageEntity.senderID)
            .child(messageEntity.companionID)
            .child("lastTimeMessageSent")
            .setValue(messageEntity.timestamp)


        messages
            .child(messageEntity.senderID)
            .child(messageEntity.companionID)
            .push()
            .setValue(messageEntity)
            .addOnSuccessListener {
                Log.d("ChatViewModel", "MessageOfUser: $it")

                messages
                    .child(messageEntity.companionID)
                    .child(messageEntity.senderID)
                    .push()
                    .setValue(messageEntity)
                    .addOnSuccessListener {
//                                _isMessageSent.value = true
                        Log.d("ChatViewModel", "MessageOfCompanion: $it")

                    }
                    .addOnFailureListener {
//                                _error.value = it.message
                        Log.d("ChatViewModel", "MessageErrorOfCompanion: " + it.message.toString())
                    }
            }
            .addOnFailureListener {
//                        _error.value = it.message
                Log.d("ChatViewModel", it.message.toString())
                Log.d("ChatViewModel", "MessageErrorOfCurrentUser: " + it.message.toString())
            }


    }

    override fun getMessages(
        currentUserID: String,
        anotherUserID: String,
        callback: (MainResultEntity) -> Unit
    ) {
        messages.child(currentUserID).child(anotherUserID)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val messagesList = arrayListOf<MessageEntity>()
                    for (message in snapshot.children) {
                        val messageEntityFromDB = message.getValue(MessageEntity::class.java)
                        if (messageEntityFromDB != null) {
                            messagesList.add(messageEntityFromDB)
                        }
                    }

                    mainResultDTO = MainResultDTO(
                        success = true,
                        messages = messagesList
                    )
                    callback(mainMapper.mapMainResultDTOToEntity(mainResultDTO))
                    Log.d("ChatViewModel", "Messages: $messagesList")
                }

                override fun onCancelled(error: DatabaseError) {
                    mainResultDTO = MainResultDTO(
                        success = false,
                        errorMessage = error.message
                    )
                    callback(mainMapper.mapMainResultDTOToEntity(mainResultDTO))
                }
            })
    }

    override fun getCurrentUser(currentUserID: String, callback: (MainResultEntity) -> Unit) {
        users.child(currentUserID).addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(CurrentUserDTO::class.java)
                mainResultDTO = MainResultDTO(
                    success = true,
                    currentUser = user
                )
                callback(mainMapper.mapMainResultDTOToEntity(mainResultDTO))

            }

            override fun onCancelled(error: DatabaseError) {
                mainResultDTO = MainResultDTO(
                    success = false,
                    errorMessage = error.message
                )
                callback(mainMapper.mapMainResultDTOToEntity(mainResultDTO))
            }

        })
    }

    companion object {
        private const val TAG = "FirebaseRepositoryImpl"
    }


}