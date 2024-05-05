package com.androider.buzzflowmessenger.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.presentation.models.FoundUser


object UsersDiffCallback : DiffUtil.ItemCallback<FoundUserEntity>() {

    override fun areItemsTheSame(oldItem: FoundUserEntity, newItem: FoundUserEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: FoundUserEntity, newItem: FoundUserEntity): Boolean {
        return oldItem == newItem
    }
}