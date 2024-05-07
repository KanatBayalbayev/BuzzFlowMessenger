package com.androider.buzzflowmessenger.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.androider.buzzflowmessenger.databinding.FragmentChatBinding
import com.androider.buzzflowmessenger.domain.models.MessageEntity
import com.androider.buzzflowmessenger.presentation.activities.MyApplication
import com.androider.buzzflowmessenger.presentation.adapters.messages.MessagesAdapter
import com.androider.buzzflowmessenger.presentation.utils.getTrimmedValue
import com.androider.buzzflowmessenger.presentation.viewmodel.MainState
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModel
import com.androider.buzzflowmessenger.presentation.viewmodel.MainViewModelFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject


class ChatFragment : Fragment() {


    private lateinit var mainViewModel: MainViewModel

    private lateinit var messagesAdapter: MessagesAdapter

    private val args by navArgs<ChatFragmentArgs>()

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory

    private val component by lazy {
        (requireActivity().application as MyApplication).component
    }

    private var _binding: FragmentChatBinding? = null
    private val binding: FragmentChatBinding
        get() {
            return _binding ?: throw IllegalStateException(
                "Binding is not initialized or has been cleared"
            )
        }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate CurrentUserID: ${args.CurrentUserID}")
        messagesAdapter = MessagesAdapter(args.CurrentUserID)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(this, mainViewModelFactory)[MainViewModel::class.java]

        attachRVtoAdapter()

        sendMessage()

        mainViewModel.getMessages(args.CurrentUserID, args.AnotherUserID)

        observeViewModel()
    }
    private fun attachRVtoAdapter(){

        binding.recyclerView.adapter = messagesAdapter
//        binding.recyclerView.itemAnimator = null
    }
    private fun observeViewModel() {
        mainViewModel.state.observe(viewLifecycleOwner) { mainState ->
            when (mainState) {
                is MainState.Messages -> {

                    mainState.messages?.let { handleMessagesList(it) }
                }
                is MainState.Error -> {
                    Log.d(TAG, "observeViewModel: ${mainState.exception}")
                }

                else -> {}
            }

        }
    }

    private fun handleMessagesList(list: ArrayList<MessageEntity>){
        Log.d(TAG, "onCreate list: $list")
//        binding.recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//
//            override fun onGlobalLayout() {
//                if (list.isEmpty()) {
//                    return
//                }
//                binding.recyclerView.scrollToPosition(list.size - 1)
//                binding.recyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//
//        })

        messagesAdapter.submitList(list)
    }

    private fun sendMessage() {
        binding.buttonToSendMessage.setOnClickListener {
            val textMessage = binding.inputMessageFromUser.getTrimmedValue()
            Log.d(TAG, "onViewCreated AnotherUserID: $textMessage")

            val messageEntity = MessageEntity(
                textMessage,
                args.CurrentUserID,
                args.AnotherUserID,
                getCurrentTime(),
                false
            )

            mainViewModel.sendMessage(messageEntity)
            binding.inputMessageFromUser.setText("")


        }
    }

    private fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val currentTime = Calendar.getInstance().time
        return dateFormat.format(currentTime)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val TAG = "ChatFragment"
    }


}