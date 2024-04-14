package com.androider.buzzflowmessenger.presentation.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentResetPasswordBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject


class ResetPasswordFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }


    private var _binding: FragmentResetPasswordBinding? = null
    private val binding: FragmentResetPasswordBinding
        get() = _binding ?: throw RuntimeException("FragmentResetPasswordBinding is null")


    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResetPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[AuthViewModel::class.java]
        navigation()
    }

    private fun navigation() {
        navigateBackToLogin()
        resetPassword()
    }

    private fun navigateBackToLogin() {
        binding.btnToBackToLoginFromReset.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun resetPassword() {
        binding.btnToReset.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.navigateToLogin)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}