package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.models.MainResultEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject


class GetMessagesUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(
        currentUserID: String,
        anotherUserID: String,
        callback: (MainResultEntity) -> Unit
    ){
        firebaseRepository.getMessages(currentUserID, anotherUserID, callback)
    }

}