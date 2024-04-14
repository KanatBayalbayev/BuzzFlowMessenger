package com.androider.buzzflowmessenger.di

import androidx.lifecycle.ViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel::class)
    fun bindMainViewModel(authViewModel: AuthViewModel): ViewModel
}