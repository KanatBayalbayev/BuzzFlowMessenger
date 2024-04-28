package com.androider.buzzflowmessenger.data.models

import android.net.Uri

data class FoundUserDTO(
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
){
    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )
}
