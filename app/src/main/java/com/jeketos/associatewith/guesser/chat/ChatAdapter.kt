package com.jeketos.associatewith.guesser.chat

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeketos.associatewith.R
import java.util.ArrayList
import kotlinx.android.synthetic.main.item_chat.view.*

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

class ChatAdapter() : RecyclerView.Adapter<ChatAdapter.ChatHolder>() {

    var chatItems : MutableList<IChatItem>

    init{
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
    }

    override fun getItemCount(): Int {
        return chatItems.size
    }

    fun updateItems(item: ChatItem?){
        if(item != null)
            chatItems.add(item)
        else
            chatItems = ArrayList<IChatItem>()
        notifyDataSetChanged()
    }

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
       val name = itemView.name!!
       val message = itemView.message!!
    }
}
