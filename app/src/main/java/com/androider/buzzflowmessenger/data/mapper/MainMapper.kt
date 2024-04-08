package com.androider.buzzflowmessenger.data.mapper

import com.androider.buzzflowmessenger.data.models.CurrentUserFirebase
import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import javax.inject.Inject

class MainMapper @Inject constructor() {

    fun mapCurrentUserFirebaseToEntity(
        currentUserFirebase: CurrentUserFirebase
    ):CurrentUserEntity{
        return CurrentUserEntity(
            id = currentUserFirebase.id,
            email = currentUserFirebase.email,
            isEmailVerified = currentUserFirebase.isEmailVerified,
            displayName = currentUserFirebase.displayName,
            photoUrl = currentUserFirebase.photoUrl,
            providerId = currentUserFirebase.providerId,
            phoneNumber = currentUserFirebase.phoneNumber,
        )
    }
}