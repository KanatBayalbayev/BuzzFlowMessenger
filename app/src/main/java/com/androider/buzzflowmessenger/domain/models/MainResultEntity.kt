package com.androider.buzzflowmessenger.domain.models

import com.androider.buzzflowmessenger.data.models.FoundUserDTO

data class MainResultEntity(
    val success: Boolean = false,
    val foundUser: FoundUserEntity? = null,
    val chats: ArrayList<FoundUserEntity>? = null,
    val messages: ArrayList<MessageEntity>? = null,
    val errorMessage: String? = null
)

