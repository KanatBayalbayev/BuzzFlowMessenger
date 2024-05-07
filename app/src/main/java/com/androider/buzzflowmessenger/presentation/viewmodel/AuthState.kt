package com.androider.buzzflowmessenger.presentation.viewmodel

import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import com.androider.buzzflowmessenger.presentation.models.CurrentUser

sealed class AuthState{
    object Loading : AuthState()
    object isSignedIn : AuthState()
    object isSignedOut : AuthState()
    data class Success(val user: CurrentUserEntity?) : AuthState()
    data class Error(val exception: String?) : AuthState()
}
