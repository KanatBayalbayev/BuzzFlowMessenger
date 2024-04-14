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
import com.androider.buzzflowmessenger.databinding.FragmentLoginBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject


class LoginFragment : Fragment() {

    private lateinit var authViewModel: AuthViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding ?: throw RuntimeException("FragmentLoginBinding is null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authViewModel = ViewModelProvider(this, mainViewModelFactory)[AuthViewModel::class.java]
        navigation()



    }


    private fun navigateToDashboard(){
        authViewModel.isLoggedIn()
    }

    private fun navigation(){
        navigateToResetPassword()
        navigateToSignUp()
    }

    private fun navigateToResetPassword(){
        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.navigateToResetPassword)
        }
    }

    private fun navigateToSignUp(){
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.navigateToSignUp)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}