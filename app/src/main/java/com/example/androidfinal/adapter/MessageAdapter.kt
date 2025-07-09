package com.example.androidfinal.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidfinal.databinding.MessageLeftBinding
import com.example.androidfinal.databinding.MessageRightBinding
import com.example.androidfinal.presentation.model.Message

class MessageAdapter : ListAdapter<Message, RecyclerView.ViewHolder>(MessageDiffUtil()) {

    companion object {
        const val LEFT_MESSAGE = 1
        const val RIGHT_MESSAGE = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == LEFT_MESSAGE) {
            return LeftMessageHolder(
                MessageLeftBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else
            return RightMessageHolder(
                MessageRightBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = getItem(position)
        if (holder is LeftMessageHolder) {
            holder.onBind(message)
        } else if (holder is RightMessageHolder) {
            holder.onBind(message)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) RIGHT_MESSAGE else LEFT_MESSAGE
    }


    inner class LeftMessageHolder(private val binding: MessageLeftBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(leftMessage: Message) {
            binding.tvMessageBox.text = leftMessage.text
            binding.tvDate.text = leftMessage.date

        }
    }

    inner class RightMessageHolder(private val binding: MessageRightBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(rightMessage: Message) {
            binding.tvMessageBox.text = rightMessage.text
            binding.tvDate.text = rightMessage.date
        }
    }
}

class MessageDiffUtil : DiffUtil.ItemCallback<Message>() {
    override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
        return oldItem == newItem
    }
}