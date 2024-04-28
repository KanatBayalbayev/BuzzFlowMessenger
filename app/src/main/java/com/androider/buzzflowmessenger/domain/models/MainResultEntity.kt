package com.androider.buzzflowmessenger.domain.models

data class MainResultEntity(
    val success: Boolean,
    val foundUser: FoundUserEntity? = null,
    val errorMessage: String? = null
)

