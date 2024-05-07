package com.androider.buzzflowmessenger.domain.models

data class MessageEntity @JvmOverloads constructor(
    val textMessage: String = "",
    val senderID: String = "",
    val companionID: String = "",
    val timestamp: String = "",
    var isSeen: Boolean = false
)
