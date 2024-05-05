package com.androider.buzzflowmessenger.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.androider.buzzflowmessenger.databinding.UserItemBinding
import com.androider.buzzflowmessenger.domain.models.FoundUserEntity
import com.androider.buzzflowmessenger.presentation.models.FoundUser
import com.squareup.picasso.Picasso

class UsersAdapter(
    private val context: Context
) : ListAdapter<FoundUserEntity, UsersViewHolder>(UsersDiffCallback) {

    var onUserClickListener: OnUserClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val user = getItem(position)
        with(holder.binding) {
            with(user) {
//                Picasso.get().load(userProfileImage).into(userIconCircleImageView)
                userName.text = name
                lastMessageUser.text = lastMessage
                lastMessageTime.text = lastTimeMessageSent

                root.setOnClickListener {
                    onUserClickListener?.onUserClick(this)
                }
            }
        }

    }

    interface OnUserClickListener {
        fun onUserClick(foundUser: FoundUserEntity)
    }
}