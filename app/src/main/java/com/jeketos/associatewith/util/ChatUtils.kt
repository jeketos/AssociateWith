package com.jeketos.associatewith.util

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.guesser.chat.ChatItem
import com.jeketos.associatewith.guesser.chat.IChatItem
import java.util.HashMap

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

object ChatUtils {

    fun  getChatItem(dataSnapshot: DataSnapshot , position : Int): IChatItem{
        val hashMap = dataSnapshot.child(position.toString()).value as HashMap<*, *>
        val name = hashMap["name"]
        val message =  hashMap["message"]
        val item = ChatItem()
        if (message is String) item.setMessage(message)
        if (name is String) item.setName(name)
        return item
    }

}