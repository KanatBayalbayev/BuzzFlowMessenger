package com.androider.buzzflowmessenger.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.presentation.utils.SharedPreferencesManager
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        lifecycleScope.launch {
            if (isOnBoardingFinished()) {
                findNavController().navigate(R.id.navigateToLogin)
            } else {
                delay(3000)
                findNavController().navigate(R.id.navigateToOnBoarding)
            }

        }

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun isOnBoardingFinished(): Boolean {
        return sharedPreferencesManager.getFinishedViewPagerContainerState(
            "onBoardingFinished",
            false
        )
    }

}