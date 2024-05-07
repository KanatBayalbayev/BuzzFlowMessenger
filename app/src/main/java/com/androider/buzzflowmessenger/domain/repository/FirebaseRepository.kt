package com.androider.buzzflowmessenger.domain.repository

import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.domain.models.MainResultEntity
import com.androider.buzzflowmessenger.domain.models.MessageEntity

interface FirebaseRepository {

    fun signUp(
        name: String,
        email: String,
        password: String,
        callback: (AuthResultEntity) -> Unit
    )

    fun signIn(email: String, password: String, callback: (AuthResultEntity) -> Unit)
    fun resetPassword(email: String)
    fun isLoggedIn(): Boolean
    fun signOut(callback: (AuthResultEntity) -> Unit)

    fun findUser(email: String, callback: (MainResultEntity) -> Unit)

    fun addFoundUserToChats(foundUser: FoundUserEntity)

    fun getChats(callback: (MainResultEntity) -> Unit)

    fun sendMessage(messageEntity: MessageEntity)

    fun getMessages(
        currentUserID: String,
        anotherUserID: String,
        callback: (MainResultEntity) -> Unit
    )


}