package com.jeketos.associatewith.chat

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jeketos.associatewith.R
import com.jeketos.associatewith.drawer.DrawerMVP
import kotlinx.android.synthetic.main.item_chat_ext.view.*
import java.util.*

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

class ExtChatAdapter(var view: DrawerMVP.DrawerView) : RecyclerView.Adapter<ExtChatAdapter.ChatHolder>() {

    var chatItems : MutableList<IChatItem>

    init{
        chatItems = ArrayList<IChatItem>()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_ext, parent, false)
        return ChatHolder(v)
    }

    override fun onBindViewHolder(holder: ChatHolder, position: Int) {
        val item = chatItems[position]
        holder.name.text = item.getName()
        holder.message.text = item.getMessage()
        setTextColor(holder, item.getColor())
        holder.likeButton.setOnClickListener {
            val chatItem = chatItems[position]
            val  color = ContextCompat.getColor(holder.name.context, R.color.green)
            chatItem.setColor(color)
            view.updateChatItemColor(chatItem, position)
            setTextColor(holder,  color)

        }
        holder.dislikeButton.setOnClickListener {
            val chatItem = chatItems[position]
            val  color = ContextCompat.getColor(holder.name.context, R.color.red)
            chatItem.setColor(color)
            view.updateChatItemColor(chatItem, position)
            setTextColor(holder,  color)
        }
    }

    private fun setTextColor(holder: ChatHolder, color: Int) {

        holder.name.setTextColor(color)
        holder.message.setTextColor(color)
        holder.colon.setTextColor(color)
    }

    override fun getItemCount(): Int {
        return chatItems.size
    }

    fun updateItems(item: IChatItem?){
        if(item != null)
            chatItems.add(item)
        else
            chatItems = ArrayList<IChatItem>()
        notifyDataSetChanged()
    }

    class ChatHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name = itemView.name!!
        val message = itemView.message!!
        val colon = itemView.colon!!
        val likeButton = itemView.likeButton!!
        val dislikeButton = itemView.dislikeButton!!

    }
}
