package com.jeketos.associatewith.ui.drawer

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.ui.chat.IChatItem
import java.util.*

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class DrawerMVP() {

    interface DrawerView {

        fun init()

        fun addChatItems(chatItem: ArrayList<IChatItem>)

        fun clearChat()

        fun showChooseWordDialog(words: Array<CharSequence>)

        fun showWinnerDialog(name: String)

        fun setStrokeWidth(strokeWidth: Float)

        fun  updateChatItemColor(chatItem: IChatItem, key: String)
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
        fun  updateChatItemColor(chatItem: IChatItem, key: String)
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
        fun  updateChatItemColor(chatItem: IChatItem, key: String)
        fun init()
    }

}

