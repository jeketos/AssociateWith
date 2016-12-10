package com.jeketos.associatewith.guesser

import android.text.TextUtils

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.di.Injector
import com.jeketos.associatewith.guesser.chat.ChatItem
import com.jeketos.associatewith.util.ChatUtils

import java.util.HashMap

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class GuesserPresenterImpl(val view: GuesserMVP.GuesserView) : GuesserMVP.GuesserPresenter {


     var model : GuesserMVP.GuesserModel
     var previousX : Float
     var previousY : Float
     var previousPointCount : Int = 0
     var previousChatCount : Int = 0
     var selectedWord : String? = null

    init {
        previousX = 0f
        previousY = 0f
        previousPointCount = 0
        model = Injector.provideGuesserModel(this)
        view.init()
    }

    override fun dataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        if(childrenCount == 0){
            view.clearBoard()
        } else {
            for (i in previousPointCount until childrenCount){
                val hashMap = dataSnapshot.child(i.toString()).value as HashMap<*,*>
                val motionEvent = hashMap["motionEvent"] as Long
                val x = (hashMap["x"] as Double).toFloat()
                val y = (hashMap["y"]as Double).toFloat()
                view.draw(previousX, previousY, Point(x,y,motionEvent.toInt()))
                previousX = x
                previousY = y
            }
        }
        previousPointCount = childrenCount
    }

    override fun chatDataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        if(childrenCount == 0){
            view.clearChat()
        } else {
            val list = Array(childrenCount - previousChatCount, { i -> i + previousChatCount})
            list.forEach {
                view.addChatItem(ChatUtils.getChatItem(dataSnapshot, it))
            }
            previousChatCount = childrenCount
        }
    }

    override fun selectedWordReceived(dataSnapshot: DataSnapshot) {
        selectedWord = dataSnapshot.value as? String
    }

    override fun winnerDataReceived(dataSnapshot: DataSnapshot) {
        if(dataSnapshot.value != null) {
            val name = dataSnapshot.value as String
            if (!TextUtils.isEmpty(name)) {
                view.showWinnerDialog(name, selectedWord!!)
            }
        }
    }

    override fun sendMessage(message: String) {
        val item = ChatItem()
        item.setName(android.os.Build.MODEL)
        item.setMessage(message)
        model.sendMessage(item)
        if(message.toLowerCase() == (selectedWord)){
            model.setWinner(item)
            view.showWinnerDialog(item.getName(), message)
        }
    }
}
