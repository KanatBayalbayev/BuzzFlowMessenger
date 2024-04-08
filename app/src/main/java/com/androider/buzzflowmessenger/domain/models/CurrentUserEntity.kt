package com.androider.buzzflowmessenger.domain.models

import android.net.Uri

data class CurrentUserEntity(
    val id: String?,
    val email: String?,
    val isEmailVerified: Boolean?,
    val displayName: String?,
    val photoUrl: Uri?,
    val providerId: String?,
    val phoneNumber: String?,
)
