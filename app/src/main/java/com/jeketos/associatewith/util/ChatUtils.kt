package com.jeketos.associatewith.util

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.chat.ChatItem
import com.jeketos.associatewith.chat.IChatItem
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

}
