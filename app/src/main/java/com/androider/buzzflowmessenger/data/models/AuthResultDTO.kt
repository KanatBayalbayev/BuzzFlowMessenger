package com.androider.buzzflowmessenger.data.models

data class AuthResultDTO(
    val success: Boolean = false,
    val isSignedOut: Boolean = false,
    val user: CurrentUserFirebase? = null,
    val errorMessage: String? = null
)
