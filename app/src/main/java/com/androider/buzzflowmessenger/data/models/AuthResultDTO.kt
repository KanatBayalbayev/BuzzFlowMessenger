package com.androider.buzzflowmessenger.data.models

data class AuthResultDTO(
    val success: Boolean = false,
    val isSignedOut: Boolean = false,
    val user: CurrentUserDTO? = null,
    val errorMessage: String? = null
)
