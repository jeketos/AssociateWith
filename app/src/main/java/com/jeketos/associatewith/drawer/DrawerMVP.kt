package com.jeketos.associatewith.drawer

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.chat.IChatItem

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class DrawerMVP() {

    interface DrawerView {

        fun init()

        fun addChatItem(chatItem: IChatItem?)

        fun clearChat()

        fun showChooseWordDialog(words: Array<CharSequence>)

        fun showWinnerDialog(name: String)

        fun setStrokeWidth(strokeWidth: Float)

        fun  updateChatItemColor(chatItem: IChatItem, position: Int)
    }

    interface DrawerPresenter : BasePresenter<DrawerView> {
        fun init()
        fun sendPoint(point: Point)
        fun chatDataReceived(dataSnapshot: DataSnapshot)
        fun clearChat()
        fun wordsDataReceived(dataSnapshot: DataSnapshot )
        fun saveWord(word: CharSequence)
        fun winnerDataReceived(dataSnapshot: DataSnapshot)
        fun clearDraw()
        fun  updateChatItemColor(chatItem: IChatItem, position: Int)
    }

    interface DrawerModel {

        fun getMovesCount() : Int

        fun sendPoint(movesCount: Int, point: Point)

        fun clearData()

        fun saveSelectedWord(word: CharSequence)

        fun addChatListener(chatListener: (DataSnapshot) -> Unit)

        fun addWordsListener(wordsListener: (DataSnapshot) -> Unit)
        fun addWinnerListener(winnerListener: (DataSnapshot) -> Unit)
        fun addEventListeners()
        fun removeListeners()
        fun clearDraw()
        fun  updateChatItemColor(chatItem: IChatItem, position: Int)
    }

}

