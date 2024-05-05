package com.androider.buzzflowmessenger.data.models

import com.androider.buzzflowmessenger.domain.models.FoundUserEntity

data class MainResultDTO(
    val success: Boolean = false,
    val user: FoundUserDTO? = null,
    val chats: ArrayList<FoundUserEntity>? = null,
    val errorMessage: String? = null
)

