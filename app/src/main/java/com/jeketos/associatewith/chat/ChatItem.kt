package com.jeketos.associatewith.chat

import android.graphics.Color

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

class ChatItem() : IChatItem {


    lateinit private var  name : String
    lateinit private var  message : String
    private var color : Int = Color.argb(0xff, 0, 0, 0)

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

    override fun getColor(): Int {
        return color
    }

    override fun setColor(color: Int) {
        this.color = color
    }
}
