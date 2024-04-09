package com.androider.buzzflowmessenger.presentation.onboarding.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentFirstScreenBinding


class FirstScreen : Fragment() {

    private var _binding: FragmentFirstScreenBinding? = null
    private val binding: FragmentFirstScreenBinding
        get() = _binding ?: throw RuntimeException("FragmentFirstScreenBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)

        binding.next.setOnClickListener {
            viewPager?.currentItem = 1

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}