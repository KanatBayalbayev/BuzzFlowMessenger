package com.androider.buzzflowmessenger.data.mapper

import com.androider.buzzflowmessenger.data.models.AuthResultDTO
import com.androider.buzzflowmessenger.data.models.MainResultDTO
import com.androider.buzzflowmessenger.domain.models.AuthResultEntity
import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.domain.models.MainResultEntity
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
                password = authResultDTO.user?.password,
                name = authResultDTO.user?.name,
                online = authResultDTO.user?.online
            ),
            errorMessage = authResultDTO.errorMessage
        )
    }

    fun mapMainResultDTOToEntity(
        mainResultDTO: MainResultDTO
    ):MainResultEntity{
        return MainResultEntity(
            success = mainResultDTO.success,
            foundUser = FoundUserEntity(
                id = mainResultDTO.user?.id,
                email = mainResultDTO.user?.email,
                password = mainResultDTO.user?.password,
                name = mainResultDTO.user?.name,
                online = mainResultDTO.user?.online,
                userProfileImage = mainResultDTO.user?.userProfileImage
            ),
            chats = mainResultDTO.chats
        )
    }
}