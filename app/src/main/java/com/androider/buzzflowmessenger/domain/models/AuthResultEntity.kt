package com.androider.buzzflowmessenger.domain.models

data class AuthResultEntity(
    val success: Boolean,
    val isSignedOut: Boolean,
    val user: CurrentUserEntity? = null,
    val errorMessage: String? = null
)

