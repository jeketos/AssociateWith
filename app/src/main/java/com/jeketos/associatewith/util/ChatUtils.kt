package com.jeketos.associatewith.util

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.ui.chat.ChatItem
import com.jeketos.associatewith.ui.chat.IChatItem
import java.util.*

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

object ChatUtils {

    fun  getChatItem(dataSnapshot: DataSnapshot , position : Int): IChatItem{
        val hashMap = dataSnapshot.child(position.toString()).value as HashMap<*, *>
        val name = hashMap["name"]
        val message =  hashMap["message"]
        val color = hashMap["color"].toString().toInt()
        val item = ChatItem()
        item.setColor(color)
        if (message is String) item.setMessage(message)
        if (name is String) item.setName(name)
        return item
    }

    fun getChatItems(dataSnapshot: DataSnapshot): ArrayList<IChatItem> {

        val list = dataSnapshot.children.toList()
        val chatItems : ArrayList<IChatItem> = ArrayList()
        list.forEach {
            val hashMap = it.value as HashMap<*, *>
            val name = hashMap["name"]
            val message =  hashMap["message"]
            val color = hashMap["color"].toString().toInt()
            val key = it.key
            val item = ChatItem()
            item.setColor(color)
            if (message is String) item.setMessage(message)
            if (name is String) item.setName(name)
            item.setKey(key)
            chatItems.add(item)
        }
        return chatItems
    }

}
