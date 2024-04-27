package com.androider.buzzflowmessenger

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.androider.buzzflowmessenger.databinding.FragmentProfileSetupBinding
import com.androider.buzzflowmessenger.databinding.FragmentSignUpBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfileSetupFragment : Fragment() {

    private lateinit var viewModel: AuthViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentProfileSetupBinding? = null
    private val binding: FragmentProfileSetupBinding
        get() = _binding ?: throw RuntimeException("FragmentProfileSetupBinding is null")

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[AuthViewModel::class.java]
        navigation()
    }

    private fun navigation() {
        navigateToDashboard()
    }

    private fun navigateToDashboard() {
        binding.btnLaunchDashboard.setOnClickListener {
            findNavController().navigate(R.id.navigateToDashboard)
        }
        binding.btnSkip.setOnClickListener {
            findNavController().navigate(R.id.navigateToDashboard)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}