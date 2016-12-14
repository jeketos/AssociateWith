package com.jeketos.associatewith.guesser

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.guesser.chat.IChatItem

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

 class GuesserMVP {

     interface GuesserView{

        fun init()

        fun draw(previousX : Float, previousY : Float, point : Point)

        fun clearBoard()

        fun clearChat()

        fun addChatItem(item : IChatItem)

        fun showWinnerDialog(name : String, word : String)
    }

     interface GuesserPresenter : BasePresenter<GuesserView>{

        fun moveDataReceived(dataSnapshot: DataSnapshot)

        fun sendMessage(message : String)

        fun chatDataReceived(dataSnapshot : DataSnapshot)

        fun selectedWordReceived(dataSnapshot : DataSnapshot)

        fun winnerDataReceived(dataSnapshot : DataSnapshot)
    }

     interface GuesserModel{

         fun sendMessage(item : IChatItem)

         fun setWinner(item : IChatItem)
         fun addChatListener(chatListener: (DataSnapshot) -> Unit)
         fun addMoveListener(moveListener: (DataSnapshot) -> Unit)
         fun addSelectedWordListener(selectedWordListener: (DataSnapshot) -> Unit)
         fun addWinnerListener(winnerListener: (DataSnapshot) -> Unit)
    }

}
