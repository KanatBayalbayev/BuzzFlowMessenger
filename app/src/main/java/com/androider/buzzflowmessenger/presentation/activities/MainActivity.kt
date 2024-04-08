package com.androider.buzzflowmessenger.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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

    companion object {
        const val TAG = "TestFireMaker"
    }
}