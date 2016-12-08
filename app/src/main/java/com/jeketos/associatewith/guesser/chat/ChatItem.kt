package com.jeketos.associatewith.guesser.chat

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

class ChatItem() : IChatItem {

    lateinit private var  name : String
    lateinit private var  message : String

    override fun getMessage(): String {
        return message
    }

    override fun setMessage(message: String) {
        this.message = message
    }

    override fun getName(): String {
        return name
    }

    override fun setName(name: String) {
       this.name = name
    }
}
