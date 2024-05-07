package com.androider.buzzflowmessenger.presentation.adapters.messages

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.androider.buzzflowmessenger.R
import com.androider.buzzflowmessenger.domain.models.MessageEntity

class MessagesAdapter(
    private val userID: String
) : ListAdapter<MessageEntity, MessagesAdapter.ViewHolder>(MessagesDiffCallback) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewMessage: TextView = view.findViewById(R.id.textMessage)
        val unreadMessage: TextView = view.findViewById(R.id.messageunRead)
        val readMessage: TextView = view.findViewById(R.id.messageRead)
        val sentMessageTime: TextView = view.findViewById(R.id.sentMessageTime)
        val messageSeen: TextView = view.findViewById(R.id.messageSeen)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var layout = 0
        layout = if (viewType == USER_MESSAGE) {
            R.layout.current_user_message
        } else {
            R.layout.another_user_message
        }
        val view = LayoutInflater.from(parent.context).inflate(
            layout,
            parent,
            false
        )
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        when (holder) {
//            is CurrentUserMessageViewHolder -> holder.bind(message)
//            is AnotherUserMessageViewHolder -> holder.bind(message)
//        }
        val message = getItem(position)
//        holder.bind(message, userID, isReadMessage, compID)
        holder.textViewMessage.text = message.textMessage
        holder.sentMessageTime.text = message.timestamp
//        if (position == currentList.size - 1){
//            if (message.senderID == userID){
//                if (message.isSeen){
//                    holder.messageSeen.text = mContext.resources.getString(R.string.seen)
//                } else {
//                    holder.messageSeen.text = mContext.resources.getString(R.string.delivered)
//                }
//            }
//        } else {
//            holder.messageSeen.visibility = View.GONE
//        }

    }



    override fun getItemViewType(position: Int): Int {
        val message = getItem(position)
        Log.d("MessagesAdapter", "UserID: $userID")
        return if (message.senderID == userID) {
            USER_MESSAGE
        } else {
            COMPANION_MESSAGE
        }
    }

    override fun onCurrentListChanged(
        previousList: MutableList<MessageEntity>,
        currentList: MutableList<MessageEntity>
    ) {
        super.onCurrentListChanged(previousList, currentList)
    }




    companion object {
        private const val USER_MESSAGE = 1
        private const val COMPANION_MESSAGE = 2

    }
}