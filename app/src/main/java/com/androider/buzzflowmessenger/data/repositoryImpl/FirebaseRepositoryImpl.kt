package com.androider.buzzflowmessenger.data.repositoryImpl

import com.androider.buzzflowmessenger.data.mapper.MainMapper
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import javax.inject.Inject

class FirebaseRepositoryImpl @Inject constructor(
    private val mainMapper: MainMapper
): FirebaseRepository {
    override fun signUp(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun login(email: String, password: String) {
        TODO("Not yet implemented")
    }


}