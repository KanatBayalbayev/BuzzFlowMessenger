package com.androider.buzzflowmessenger.presentation.adapters.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.androider.buzzflowmessenger.databinding.AnotherUserMessageBinding
import com.androider.buzzflowmessenger.domain.models.MessageEntity

class AnotherUserMessageViewHolder(val binding: AnotherUserMessageBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MessageEntity) {

    }

    companion object {
        fun from(parent: ViewGroup): AnotherUserMessageViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = AnotherUserMessageBinding.inflate(inflater, parent, false)
            return AnotherUserMessageViewHolder(binding)
        }
    }
}