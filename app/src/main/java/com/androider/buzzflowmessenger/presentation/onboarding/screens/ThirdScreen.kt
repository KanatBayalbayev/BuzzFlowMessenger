package com.androider.buzzflowmessenger.presentation.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentSecondScreenBinding
import com.androider.buzzflowmessenger.databinding.FragmentThirdScreenBinding
import com.androider.buzzflowmessenger.presentation.utils.SharedPreferencesManager


class ThirdScreen : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding: FragmentThirdScreenBinding
        get() = _binding ?: throw RuntimeException("FragmentThirdScreenBinding is null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.btnGetStarted.setOnClickListener {
            onBoardingFinished()
            findNavController().navigate(R.id.navigateToLogin)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onBoardingFinished(){
        sharedPreferencesManager.saveFinishedViewPagerContainerState("onBoardingFinished", true)

    }


}