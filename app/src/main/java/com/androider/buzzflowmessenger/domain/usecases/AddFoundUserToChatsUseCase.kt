package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.domain.models.MainResultEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.androider.buzzflowmessenger.presentation.models.FoundUser
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject


class AddFoundUserToChatsUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(
        foundUser: FoundUserEntity
    ){
         firebaseRepository.addFoundUserToChats(foundUser)
    }


}