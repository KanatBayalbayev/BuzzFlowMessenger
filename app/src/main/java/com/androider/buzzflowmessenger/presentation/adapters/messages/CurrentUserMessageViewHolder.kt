package com.androider.buzzflowmessenger.presentation.adapters.messages

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androider.buzzflowmessenger.databinding.CurrentUserMessageBinding
import com.androider.buzzflowmessenger.domain.models.MessageEntity

class CurrentUserMessageViewHolder(val binding: CurrentUserMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MessageEntity) {
        Log.d(TAG, "bind textMessage: ${item.textMessage}")
        binding.textMessage.text = item.textMessage
        binding.sentMessageTime.text = item.timestamp
    }

    companion object {
        private const val TAG = "CurrentUserMessageViewHolder"
        fun from(parent: ViewGroup): CurrentUserMessageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = CurrentUserMessageBinding.inflate(inflater, parent, false)
            return CurrentUserMessageViewHolder(binding)
        }
    }


}