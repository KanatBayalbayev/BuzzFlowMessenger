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
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.adapters.users.UsersAdapter
import com.androider.buzzflowmessenger.presentation.utils.SharedPreferencesManager
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthState
import com.androider.buzzflowmessenger.presentation.viewmodel.AuthViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainState
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import java.io.IOException
import java.net.SocketTimeoutException
import javax.inject.Inject


class DashboardFragment :
    Fragment(),
    ExitDialogFragment.DialogListener,
    FindUserDialogFragment.DialogListener {

    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    private lateinit var authViewModel: AuthViewModel
    private lateinit var mainViewModel: MainViewModel

    private lateinit var usersAdapter: UsersAdapter

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
        sharedPreferencesManager = SharedPreferencesManager(requireContext())
        usersAdapter = UsersAdapter(requireContext())

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
        authViewModel = ViewModelProvider(this, mainViewModelFactory)[AuthViewModel::class.java]
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]
        showDialogExit()
        observeViewModel()
        findUser()
        attachRVtoAdapter()
        handleOnUserClick()

        Log.d(TAG, "onViewCreated getCurrentUserID: ${getCurrentUserID()}")


    }

    private fun attachRVtoAdapter(){

        binding.recyclerView.adapter = usersAdapter
//        binding.recyclerView.itemAnimator = null
    }
    private fun handleOnUserClick(){
        usersAdapter.onUserClickListener = object : UsersAdapter.OnUserClickListener{
            
            override fun onUserClick(foundUser: FoundUserEntity) {
                Log.d(TAG, "onUserClick: $foundUser")
                openChatFragment(getCurrentUserID()!!, foundUser.id!!)
            }

        }
    }
    private fun getCurrentUserID(): String? {
        return sharedPreferencesManager.getCurrentUserID()
    }

    private fun openChatFragment(currentUserId: String, compUserId: String) {
        findNavController().navigate(
            DashboardFragmentDirections
                .navigateToChat(currentUserId, compUserId)
        )
    }


    private fun findUser(){
        binding.btnFindUser.setOnClickListener {
            showFindUserDialog()
        }
    }



    private fun showDialogExit(){
        val isSpecialMode = arguments?.getBoolean("is_special_mode") ?: false
        if (isSpecialMode){
            showCustomDialog()
        }
    }
    private fun observeViewModel(){
        authViewModel.state.observe(viewLifecycleOwner){
            when(it){
                is AuthState.isSignedOut -> {
                    findNavController().navigate(R.id.navigateToLogin)
                }
                else -> {}
            }
        }

        mainViewModel.getChats()
        mainViewModel.state.observe(viewLifecycleOwner){
            when(it){
                is MainState.Loading -> {
                    showLoading()
                }
                is MainState.Chats -> {
                    hideLoading()
                    Log.d(TAG, "observeViewModel chats: ${it.chats}")
                    usersAdapter.submitList(it.chats)
                }
                is MainState.Error -> {
                    hideLoading()
                    Toast.makeText(requireContext(), "${it.exception}", Toast.LENGTH_LONG).show()
                }

                else -> {}
            }
        }
    }

    private fun showLoading() {
        binding.recyclerView.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.recyclerView.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }
    private fun showCustomDialog() {
        ExitDialogFragment.showDialog(parentFragmentManager, this)
    }
    private fun showFindUserDialog() {
        FindUserBottomSheetDialogFragment().show(parentFragmentManager, "MyBottomSheetDialogFragment")
    }
    override fun onConfirm() {
        try {
            authViewModel.signOut()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "DashboardFragment"
    }


}