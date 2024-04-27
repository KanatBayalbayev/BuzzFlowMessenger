package com.androider.buzzflowmessenger.di

import com.androider.buzzflowmessenger.ProfileSetupFragment
import com.androider.buzzflowmessenger.presentation.fragments.DashboardFragment
import com.androider.buzzflowmessenger.presentation.fragments.ResetPasswordFragment
import com.androider.buzzflowmessenger.presentation.fragments.SignUpFragment
import com.androider.buzzflowmessenger.presentation.activities.MainActivity
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.fragments.LoginFragment
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(myApplication: MyApplication)
    fun inject(mainActivity: MainActivity)
    fun inject(signUpFragment: SignUpFragment)
    fun inject(loginFragment: LoginFragment)
    fun inject(resetPasswordFragment: ResetPasswordFragment)
    fun inject(profileSetupFragment: ProfileSetupFragment )
    fun inject(dashboardFragment: DashboardFragment)

    @Component.Factory
    interface ComponentFactory{

        fun create(): ApplicationComponent
    }
}