package com.androider.buzzflowmessenger.di

import com.androider.buzzflowmessenger.data.repositoryImpl.FirebaseRepositoryImpl
import com.androider.buzzflowmessenger.domain.repository.FirebaseRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindFirebaseRepository(firebaseRepositoryImpl: FirebaseRepositoryImpl): FirebaseRepository


    companion object{
        @Provides
        @ApplicationScope
        fun provideFirebaseAuth(): FirebaseAuth {
            return FirebaseAuth.getInstance()
        }

        @Provides
        @ApplicationScope
        fun provideFirestore(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }
    }
}