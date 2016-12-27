package com.jeketos.associatewith.chat

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeketos.associatewith.R
import kotlinx.android.synthetic.main.item_chat.view.*
import java.util.*

/**
 * Created by eugene.kotsogub on 12/27/16.
 *
 */
class ChatAdapter() : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    var chatItems: MutableList<IChatItem>

    init {
        chatItems = ArrayList<IChatItem>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatHolder(v)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val item = chatItems[position]
        holder.name.text = item.getName()
        holder.message.text = item.getMessage()
        setTextColor(holder, item.getColor())
    }

    private fun setTextColor(holder: ChatHolder, color: Int) {
        holder.name.setTextColor(color)
        holder.message.setTextColor(color)
        holder.colon.setTextColor(color)
    }

    override fun getItemCount(): Int {
        return chatItems.size
    }

    fun updateItems(items: ArrayList<IChatItem>?) {
        if (items != null)
            chatItems = items
        else
            chatItems = ArrayList<IChatItem>()
        notifyDataSetChanged()
    }

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.chatName!!
        val message = itemView.chatMessage!!
        val colon = itemView.chatColon!!

    }
}