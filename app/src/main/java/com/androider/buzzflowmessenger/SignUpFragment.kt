package com.androider.buzzflowmessenger

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.databinding.FragmentSignUpBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.utils.getTrimmedValue
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import com.google.android.material.textfield.TextInputLayout
import javax.inject.Inject


class SignUpFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

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
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        navigation()
    }


    private fun signUp() {
        binding.btnSignUp.setOnClickListener {
            makeEditTextRed()
            signUpUser()
        }
    }


    private fun signUpUser() {
        val name = binding.inputName.getTrimmedValue()
        val email = binding.inputEmail.getTrimmedValue()
        val password = binding.inputPassword.getTrimmedValue()

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_LONG).show()
        } else {
//            // Скрыть кнопку регистрации и показать индикатор загрузки
//            binding.buttonToRegister.visibility = View.GONE
//            binding.loaderLoginButton.visibility = View.VISIBLE

            // Выполнить регистрацию
            viewModel.signUp(name, email, password)
            navigateToLogin()
        }
    }


    private fun makeEditTextRed() {
        highlightEmptyField(binding.inputName, binding.tilName, getRedColor())
        highlightEmptyField(binding.inputEmail, binding.tilEmail, getRedColor())
        highlightEmptyField(binding.inputPassword, binding.tilPassword, getRedColor())
    }


    private fun highlightEmptyField(
        editText: EditText,
        textInputLayout: TextInputLayout,
        color: Int
    ) {
        if (editText.text.toString().trim().isEmpty()) {
            textInputLayout.boxStrokeColor = color
            editText.requestFocus()
        }
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
    }

    private fun navigateToLogin() {
        findNavController().navigate(R.id.navigateToLogin)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}