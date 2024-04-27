package com.androider.buzzflowmessenger.presentation.models

import android.net.Uri

data class CurrentUser(
    val id: String?,
    val email: String?,
    val isEmailVerified: Boolean?,
    val displayName: String?,
    val photoUrl: Uri?,
    val providerId: String?,
    val phoneNumber: String?,
)
