package com.androider.buzzflowmessenger.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentDashboardBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import com.androider.buzzflowmessenger.presentation.viewmodel.SharedViewModel
import javax.inject.Inject


class DashboardFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding
        get() = _binding ?: throw RuntimeException("FragmentDashboardBinding is null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[AuthViewModel::class.java]
        val isSpecialMode = arguments?.getBoolean("is_special_mode") ?: false
        if (isSpecialMode){
            Toast.makeText(requireContext(), "Data: $isSpecialMode", Toast.LENGTH_SHORT).show()
        }



        viewModel.state.observe(viewLifecycleOwner){
            when(it){
                is AuthState.isSignedOut -> {
                    findNavController().navigate(R.id.navigateToLogin)
                }

                else -> {}
            }
        }

        binding.btnLogout.setOnClickListener {
            viewModel.signOut()
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}