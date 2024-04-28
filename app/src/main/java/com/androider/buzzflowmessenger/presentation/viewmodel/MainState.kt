package com.androider.buzzflowmessenger.presentation.viewmodel

import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.presentation.models.CurrentUser
import com.androider.buzzflowmessenger.presentation.models.FoundUser

sealed class MainState{
    object Loading : MainState()
    data class Success(val user: FoundUserEntity?) : MainState()
    data class Error(val exception: String?) : MainState()
}
