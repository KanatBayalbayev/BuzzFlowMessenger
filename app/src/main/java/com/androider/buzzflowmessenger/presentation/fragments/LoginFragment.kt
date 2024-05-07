package com.androider.buzzflowmessenger.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentLoginBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.utils.FunctionUtils
import com.androider.buzzflowmessenger.presentation.utils.SharedPreferencesManager
import com.androider.buzzflowmessenger.presentation.utils.getTrimmedValue
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject


class LoginFragment : Fragment() {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var viewModel: AuthViewModel

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
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
        viewModel = ViewModelProvider(this, mainViewModelFactory)[AuthViewModel::class.java]
        navigation()
        signIn()
        viewModel.getStateLoggedIn()
        viewModel.state.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                is AuthState.isSignedIn -> {
                    findNavController().navigate(R.id.navigateToDashboard)
                }
                is AuthState.Success -> {
                    findNavController().navigate(R.id.navigateToDashboard)

                    sharedPreferencesManager.saveCurrentUserID(authState.user?.id.toString())
                }
                is AuthState.Loading -> {
                    showLoading()
                }
                is AuthState.Error -> {
                    hideLoading()
                    if (authState.exception != null) {
                        showError(authState.exception)
                    }
                }
                else -> {}
            }
        }


    }

    private fun signIn(){
        binding.btnSignIn.setOnClickListener {
            val email = binding.inputEmail.getTrimmedValue()
            val password = binding.inputPassword.getTrimmedValue()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_LONG).show()
            } else {
                if (FunctionUtils.isNetworkAvailable(requireContext())) {
                    viewModel.signIn(email, password)
                } else {
                    Toast.makeText(
                        context,
                        "Интернет-соединение недоступно. Попробуйте позже.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        hideKeyboard()
    }

    private fun hideKeyboard() {
        FunctionUtils.hideKeyboard(requireContext(), requireView())
    }
    private fun showError(error: String) {
        Log.d("SignUpFragment", "showError: $error")
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()

    }

    private fun showLoading() {
        binding.btnSignIn.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.btnSignIn.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }


    private fun navigateToDashboard() {
        viewModel.getStateLoggedIn()
    }

    private fun navigation() {
        navigateToResetPassword()
        navigateToSignUp()
    }

    private fun navigateToResetPassword() {
        binding.btnResetPassword.setOnClickListener {
            findNavController().navigate(R.id.navigateToResetPassword)
        }
    }

    private fun navigateToSignUp() {
        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.navigateToSignUp)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}