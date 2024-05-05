package com.androider.buzzflowmessenger

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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

    private var selectedImageUri: Uri? = null
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

        setupProfile()
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

    private fun setupProfile(){
        binding.buttonUserIconProfileUpload.setOnClickListener {
            openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            val bitmap = selectedImageUri.let {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it)
            }
            if (bitmap != null) {
                binding.userIconProfileCircleImageView.visibility = View.VISIBLE
                binding.userIconProfileImageView.visibility = View.GONE
                binding.userIconProfileCircleImageView.setImageBitmap(bitmap)
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}