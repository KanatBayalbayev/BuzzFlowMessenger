package com.androider.buzzflowmessenger.data.models

data class CurrentUserDTO(
    val id: String?,
    val email: String?,
    var password: String?,
    var name: String?,
    var online: Boolean?,
    var userProfileImage: String? = "",
    var lastTimeVisit: String? = "",
    var lastMessage: String? = "",
    var lastTimeMessageSent: String? = "",
    var isTyping: Boolean? = false
)
