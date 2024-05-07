package com.androider.buzzflowmessenger.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.databinding.FragmentProfileSetupBinding
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import javax.inject.Inject

class ProfileFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentProfileSetupBinding? = null
    private val binding: FragmentProfileSetupBinding
        get() {
            return _binding ?: throw IllegalStateException(
                "Binding is not initialized or has been cleared")
        }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


}