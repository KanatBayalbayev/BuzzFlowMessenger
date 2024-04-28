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
import com.androider.buzzflowmessenger.presentation.CustomDialogFragment
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import com.androider.buzzflowmessenger.presentation.viewmodel.SharedViewModel
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class DashboardFragment : Fragment(), CustomDialogFragment.DialogListener {

    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentDashboardBinding? = null
    private val binding: FragmentDashboardBinding
        get() {
            return _binding ?: throw IllegalStateException(
                "Binding is not initialized or has been cleared")
        }

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
//            val dialog = CustomDialogFragment()
//            dialog.listener = this
//            dialog.show(parentFragmentManager, "CustomDialog")
            showCustomDialog()
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



    }

    private fun showCustomDialog() {
        CustomDialogFragment.showDialog(parentFragmentManager, this)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onConfirm() {
        try {
            viewModel.signOut()
            Log.i(TAG, "User signed out successfully.")
        } catch (e: SocketTimeoutException) {
            Log.w(TAG, "Sign-out timed out.", e)
        } catch (e: IOException) {
            Log.e(TAG, "IO error during sign-out.", e)
        } catch (e: Exception) {
            Log.e(TAG, "Unexpected error during sign-out.", e)
        }
    }

    override fun onCancel() {
        Log.d(TAG, "Sign-out canceled.")
    }

    companion object {
        private const val TAG = "DashboardFragment"
    }


}