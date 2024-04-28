package com.androider.buzzflowmessenger.data.models

import com.androider.buzzflowmessenger.data.models.CurrentUserFirebase

data class MainResultDTO(
    val success: Boolean,
    val user: FoundUserDTO? = null,
    val errorMessage: String? = null
)

