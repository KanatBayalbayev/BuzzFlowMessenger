package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import com.androider.buzzflowmessenger.domain.models.MessageEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject


class SendMessageUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(
        messageEntity: MessageEntity,
        currentUserEntity: CurrentUserEntity
    ){
         firebaseRepository.sendMessage(messageEntity, currentUserEntity)
    }


}