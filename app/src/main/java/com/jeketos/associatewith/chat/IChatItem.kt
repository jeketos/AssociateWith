package com.jeketos.associatewith.chat

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */
interface IChatItem {


    fun getMessage() : String

    fun setMessage(message : String)

    fun getName() : String

    fun setName(name : String)

    fun getColor() : Int

    fun setColor(color : Int)

}
