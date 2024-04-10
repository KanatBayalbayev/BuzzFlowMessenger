package com.androider.buzzflowmessenger.di

import com.androider.buzzflowmessenger.DashboardFragment
import com.androider.buzzflowmessenger.ResetPasswordFragment
import com.androider.buzzflowmessenger.SignUpFragment
import com.androider.buzzflowmessenger.presentation.activities.MainActivity
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.fragments.LoginFragment
import dagger.Component

@ApplicationScope
@Component(modules = [FirebaseModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(myApplication: MyApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(resetPasswordFragment: ResetPasswordFragment)
    fun inject(dashboardFragment: DashboardFragment)

    @Component.Factory
    interface ComponentFactory{

        fun create(): ApplicationComponent
    }
}