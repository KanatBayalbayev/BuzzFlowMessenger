package com.androider.buzzflowmessenger.presentation

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.androider.buzzflowmessenger.databinding.FragmentBottomSheetFindUserBinding
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.utils.FunctionUtils
import com.androider.buzzflowmessenger.presentation.utils.getTrimmedValue
import com.androider.buzzflowmessenger.presentation.viewmodel.MainState
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import javax.inject.Inject

class FindUserBottomSheetDialogFragment: BottomSheetDialogFragment() {

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private lateinit var viewModel: MainViewModel

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }


    private var _binding: FragmentBottomSheetFindUserBinding? = null
    private val binding: FragmentBottomSheetFindUserBinding
        get() {
            return _binding ?: throw IllegalStateException(
                "Binding is not initialized or has been cleared")
        }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetFindUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        binding.btnClose.setOnClickListener {
            dismiss()
//            binding.foundUserContainer.visibility = View.GONE
        }

        binding.actionButton.setOnClickListener {
            val email = binding.inputFindUser.getTrimmedValue()
            if (email.isEmpty()){
                Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_LONG).show()
            } else {
                if (FunctionUtils.isNetworkAvailable(requireContext())) {
                    val emailUser = email.lowercase()
                    Log.d(TAG, emailUser)
                    viewModel.findUser(emailUser)
//                    binding.foundUserContainer.visibility = View.VISIBLE
                } else {
                    Toast.makeText(
                        context,
                        "Интернет-соединение недоступно. Попробуйте позже.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.state.observe(viewLifecycleOwner){mainState ->
            when(mainState){
                is MainState.Loading -> {
                    showLoading()
                    Log.d(TAG, "observeViewModel: Loading")
                }
                is MainState.Success -> {
                    Log.d(TAG, "observeViewModel: Success")
                    Toast.makeText(
                        context,
                        "Found User SUCCESS",
                        Toast.LENGTH_LONG
                    ).show()
                    hideLoading()
                    observeFoundUser(mainState.user!!)
                }
                is MainState.Error -> {
                    Log.d(TAG, "observeViewModel: Error")
                    hideLoadingOfError()
                    Toast.makeText(
                        context,
                        "${mainState.exception}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
    private fun observeFoundUser(foundUserEntity: FoundUserEntity){
        binding.foundUserName.text = foundUserEntity.name
    }

    private fun showLoading() {
        binding.foundUserContainer.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.foundUserContainer.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
    private fun hideLoadingOfError() {
        binding.foundUserContainer.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }

    companion object{
        private const val TAG = "FindUserBottomSheetDialogFragment"
    }
}