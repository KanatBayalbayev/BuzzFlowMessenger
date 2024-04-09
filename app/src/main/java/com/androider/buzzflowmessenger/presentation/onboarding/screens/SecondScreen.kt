package com.androider.buzzflowmessenger.presentation.onboarding.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentSecondScreenBinding


class SecondScreen : Fragment() {

    private var _binding: FragmentSecondScreenBinding? = null
    private val binding: FragmentSecondScreenBinding
        get() = _binding ?: throw RuntimeException("FragmentFirstScreenBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.next.setOnClickListener {
            viewPager?.currentItem = 2

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}