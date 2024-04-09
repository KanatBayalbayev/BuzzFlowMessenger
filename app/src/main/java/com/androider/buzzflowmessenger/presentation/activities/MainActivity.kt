package com.androider.buzzflowmessenger.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.androider.buzzflowmessenger.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class MainActivity : AppCompatActivity() {


    private val component by lazy {
        (application as MyApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

}