package com.androider.buzzflowmessenger.presentation.viewmodel

import com.androider.buzzflowmessenger.domain.models.CurrentUserEntity
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.domain.models.MessageEntity

sealed class MainState{
    object Loading : MainState()
    data class Success(val user: FoundUserEntity?) : MainState()
    data class CurrentUser(val currentUser: CurrentUserEntity?) : MainState()
    data class Chats(val chats: ArrayList<FoundUserEntity>?) : MainState()
    data class Messages(val messages: ArrayList<MessageEntity>?) : MainState()
    data class Error(val exception: String?) : MainState()
}
