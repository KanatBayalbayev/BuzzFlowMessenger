package com.androider.buzzflowmessenger.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androider.buzzflowmessenger.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import javax.inject.Inject

class MainActivity : AppCompatActivity() {


    val component by lazy {
        (application as MyApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }

    companion object {
        const val TAG = "TestFireMaker"
    }
}