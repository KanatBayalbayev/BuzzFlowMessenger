package com.androider.buzzflowmessenger.di

import com.androider.buzzflowmessenger.presentation.activities.MainActivity
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import dagger.Component

@ApplicationScope
@Component(modules = [FirebaseModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(myApplication: MyApplication)

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface ComponentFactory{

        fun create(): ApplicationComponent
    }
}