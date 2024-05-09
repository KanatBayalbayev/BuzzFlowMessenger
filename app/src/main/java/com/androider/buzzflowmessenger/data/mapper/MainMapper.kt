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
    ): AuthResultEntity {
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
    ): MainResultEntity {
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
            chats = mainResultDTO.chats,
            messages = mainResultDTO.messages,
            currentUserEntity = CurrentUserEntity(
                id = mainResultDTO.currentUser?.id,
                email = mainResultDTO.currentUser?.email,
                password = mainResultDTO.currentUser?.password,
                name = mainResultDTO.currentUser?.name,
                online = mainResultDTO.currentUser?.online,
                userProfileImage = mainResultDTO.currentUser?.userProfileImage,
                lastTimeVisit = mainResultDTO.currentUser?.lastTimeVisit,
                lastMessage = mainResultDTO.currentUser?.lastMessage,
                lastTimeMessageSent = mainResultDTO.currentUser?.lastTimeMessageSent,
                isTyping = mainResultDTO.currentUser?.isTyping,
            )
        )
    }
}
