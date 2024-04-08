package com.androider.buzzflowmessenger.data.repositoryImpl

import com.androider.buzzflowmessenger.data.mapper.MainMapper
import com.androider.buzzflowmessenger.domain.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val mainMapper: MainMapper
): FirebaseRepository {

    override fun hello(): String {
        return "Hello"
    }
}