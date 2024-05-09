package com.androider.buzzflowmessenger.domain.usecases

import com.androider.buzzflowmessenger.domain.models.MainResultEntity
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject


class GetCurrentUserUseCase @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) {

    operator fun invoke(
        currentUserID: String,
        callback: (MainResultEntity) -> Unit
    ){
        firebaseRepository.getCurrentUser(currentUserID, callback)
    }

}