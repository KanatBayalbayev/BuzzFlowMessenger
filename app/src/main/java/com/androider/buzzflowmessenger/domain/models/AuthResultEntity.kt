package com.androider.buzzflowmessenger.domain.models

import com.androider.buzzflowmessenger.data.models.CurrentUserFirebase

data class AuthResultEntity(
    val success: Boolean,
    val isSignedOut: Boolean,
    val user: CurrentUserEntity? = null,
    val errorMessage: String? = null
)

