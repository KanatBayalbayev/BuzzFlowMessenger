package com.androider.buzzflowmessenger.di

import com.androider.buzzflowmessenger.data.repositoryImpl.FirebaseRepositoryImpl
import com.androider.buzzflowmessenger.domain.FirebaseRepository
import dagger.Binds
import dagger.Module

@Module
interface FirebaseModule {

    @Binds
    @ApplicationScope
    fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository
}