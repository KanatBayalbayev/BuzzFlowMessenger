package com.androider.buzzflowmessenger.data.mapper

import com.androider.buzzflowmessenger.data.models.AuthResultDTO
import com.androider.buzzflowmessenger.data.models.CurrentUserFirebase
import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import javax.inject.Inject

class MainMapper @Inject constructor() {

    fun mapAuthResultDTOToEntity(
        authResultDTO: AuthResultDTO
    ):AuthResultEntity{
        return AuthResultEntity(
            success = authResultDTO.success,
            isSignedOut = authResultDTO.isSignedOut,
            user = CurrentUserEntity(
                id = authResultDTO.user?.id,
                email = authResultDTO.user?.email,
                isEmailVerified =authResultDTO.user?.isEmailVerified,
                displayName = authResultDTO.user?.displayName,
                photoUrl = authResultDTO.user?.photoUrl,
                providerId = authResultDTO.user?.providerId,
                phoneNumber = authResultDTO.user?.phoneNumber,
            ),
            errorMessage = authResultDTO.errorMessage
        )
    }
}