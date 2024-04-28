package com.androider.buzzflowmessenger.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentSignUpBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.utils.FunctionUtils
import com.androider.buzzflowmessenger.presentation.utils.getTrimmedValue
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import com.google.api.LogDescriptor
import javax.inject.Inject


class SignUpFragment : Fragment() {


    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var  viewModel: AuthViewModel

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding is null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[AuthViewModel::class.java]
        navigation()

        observeAuthViewModel()
    }


    private fun observeAuthViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { authState ->
            when (authState) {
                is AuthState.Loading -> {
                    showLoading()
                }

                is AuthState.Success -> {
                    navigateToProfileSetup()
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

    private fun showLoading() {
        binding.btnSignUp.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.btnSignUp.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun showError(error: String) {
        Log.d("SignUpFragment", "showError: $error")
        Toast.makeText(requireContext(), error, Toast.LENGTH_LONG).show()

    }

    private fun hideKeyboard() {
        FunctionUtils.hideKeyboard(requireContext(), requireView())
    }


    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            makeEditTextRed()
            signUpUser()
            hideKeyboard()
        }
    }


    private fun signUpUser() {
        val name = binding.inputName.getTrimmedValue()
        val email = binding.inputEmail.getTrimmedValue()
        val password = binding.inputPassword.getTrimmedValue()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_LONG).show()
        } else {
            if (FunctionUtils.isNetworkAvailable(requireContext())) {
                viewModel.signUp(name, email, password)
            } else {
                Toast.makeText(
                    context,
                    "Интернет-соединение недоступно. Попробуйте позже.",
                    Toast.LENGTH_LONG
                ).show()
            }

//            navigateToLogin()
        }
    }

    private fun closeKeyboard() {
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView: View? = requireActivity().currentFocus

        currentFocusedView?.let { view ->
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private fun makeEditTextRed() {
        FunctionUtils.highlightEmptyField(binding.inputName, binding.tilName, getRedColor())
        FunctionUtils.highlightEmptyField(binding.inputEmail, binding.tilEmail, getRedColor())
        FunctionUtils.highlightEmptyField(binding.inputPassword, binding.tilPassword, getRedColor())
    }

    private fun getRedColor(): Int {
        return resources.getColor(R.color.red)
    }


    private fun navigation() {
        navigateBackToLogin()
        signUp()
    }

    private fun navigateBackToLogin() {
        binding.btnToBackToLoginFromSignUp.setOnClickListener {
            navigateToLogin()
        }
        binding.loginButton.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.navigateToLogin)
    }

    private fun navigateToProfileSetup() {
        findNavController().navigate(R.id.navigateToProfileSetup)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}