package com.androider.buzzflowmessenger.presentation.adapters.messages

import androidx.recyclerview.widget.DiffUtil
import com.androider.buzzflowmessenger.domain.models.MessageEntity


object MessagesDiffCallback : DiffUtil.ItemCallback<MessageEntity>() {

    override fun areItemsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: MessageEntity, newItem: MessageEntity): Boolean {
        return oldItem == newItem
    }
}