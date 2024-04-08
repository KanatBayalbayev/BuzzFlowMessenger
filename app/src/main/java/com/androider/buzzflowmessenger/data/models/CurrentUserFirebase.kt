package com.androider.buzzflowmessenger.data.models

import android.net.Uri

data class CurrentUserFirebase(
    val id: String?,
    val email: String?,
    val isEmailVerified: Boolean?,
    val displayName: String?,
    val photoUrl: Uri?,
    val providerId: String?,
    val phoneNumber: String?,
)
