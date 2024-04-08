package com.androider.buzzflowmessenger.presentation.activities

import android.app.Application
import com.androider.buzzflowmessenger.di.DaggerApplicationComponent

class MyApplication: Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create()
    }


    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}