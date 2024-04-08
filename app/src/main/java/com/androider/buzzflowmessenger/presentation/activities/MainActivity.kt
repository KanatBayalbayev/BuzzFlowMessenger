package com.androider.buzzflowmessenger.presentation.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androider.buzzflowmessenger.R

class MainActivity : AppCompatActivity() {

    val component by lazy {
        (application as MyApplication).component
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}