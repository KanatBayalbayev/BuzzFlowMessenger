package com.androider.buzzflowmessenger.presentation.models

import android.net.Uri

data class FoundUser(
    val id: String = "",
    val email: String = "",
    var password: String = "",
    var name: String = "",
    var online: Boolean? = false,
    var userProfileImage: String? = "",
    var lastTimeVisit: String? = "",
    var lastMessage: String? = "",
    var lastTimeMessageSent: String? = "",
    var isTyping: Boolean? = false
)
