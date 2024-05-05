package com.androider.buzzflowmessenger.presentation.viewmodel

import com.androider.buzzflowmessenger.domain.models.FoundUserEntity

sealed class MainState{
    object Loading : MainState()
    data class Success(val user: FoundUserEntity?) : MainState()
    data class Chats(val chats: ArrayList<FoundUserEntity>?) : MainState()
    data class Error(val exception: String?) : MainState()
}
