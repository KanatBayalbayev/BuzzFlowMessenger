package com.androider.buzzflowmessenger.data.models

import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.domain.models.MessageEntity

data class MainResultDTO(
    val success: Boolean = false,
    val user: FoundUserDTO? = null,
    val chats: ArrayList<FoundUserEntity>? = null,
    val messages: ArrayList<MessageEntity>? = null,
    val errorMessage: String? = null
)

