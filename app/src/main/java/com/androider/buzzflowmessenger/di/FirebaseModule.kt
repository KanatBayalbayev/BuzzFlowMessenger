package com.androider.buzzflowmessenger.di

import com.androider.buzzflowmessenger.data.repositoryImpl.FirebaseRepositoryImpl
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface FirebaseModule {

    @Binds
    @ApplicationScope
    fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository


    companion object{
        @Provides
        @ApplicationScope
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }
    }
}