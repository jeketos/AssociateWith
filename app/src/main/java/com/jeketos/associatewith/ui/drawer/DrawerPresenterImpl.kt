package com.jeketos.associatewith.ui.drawer

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.base.BaseMvpPresenter
import com.jeketos.associatewith.ui.chat.IChatItem
import com.jeketos.associatewith.util.ChatUtils
import java.util.*
import javax.inject.Inject

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class DrawerPresenterImpl @Inject constructor() : BaseMvpPresenter<DrawerMVP.DrawerView>(), DrawerMVP.DrawerPresenter {


    @Inject lateinit var model : DrawerMVP.DrawerModel
    var movesCount : Int = 0
    var previousChatCount : Int = 0

   override fun init() {
        previousChatCount = 0
//        model.clearData()
        clearChat()
        model.addChatListener({ dataSnap: DataSnapshot -> chatDataReceived(dataSnap)})
        model.addWinnerListener({ dataSnap: DataSnapshot -> winnerDataReceived(dataSnap)})
        model.addWordsListener({ dataSnap: DataSnapshot -> wordsDataReceived(dataSnap)})
        movesCount = model.getMovesCount()
    }

    override fun sendPoint(point: Point) {
        model.sendPoint(movesCount, point)
        movesCount++


    }

    override fun chatDataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        if(childrenCount != 0){
            view?.addChatItems(ChatUtils.getChatItems(dataSnapshot))
        }
        previousChatCount = childrenCount
    }

    override fun clearChat() {
        view?.clearChat()
    }

    override fun wordsDataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        val random = Random()
        if(childrenCount != 0){
            val words : Array<CharSequence> = Array(3, {i -> dataSnapshot.child(random.nextInt(childrenCount).toString()).value as CharSequence})
            view?.showChooseWordDialog(words)
        }
    }

    override fun clearDraw() {
        model.clearDraw()
        movesCount = 0
    }

    override fun saveWord(word: CharSequence) {
        model.saveSelectedWord(word)
    }

    override fun winnerDataReceived(dataSnapshot: DataSnapshot) {
        val name = dataSnapshot.value as String?
        if (name != null) {
            model.clearData()
            view?.showWinnerDialog(name)
        }
    }

    override fun attachView(view: DrawerMVP.DrawerView) {
        super.attachView(view)
        model.init()
        model.addEventListeners()
        model.addChatListener({ dataSnap: DataSnapshot -> chatDataReceived(dataSnap)})
        model.addWinnerListener({ dataSnap: DataSnapshot -> winnerDataReceived(dataSnap)})
        model.addWordsListener({ dataSnap: DataSnapshot -> wordsDataReceived(dataSnap)})
    }

    override fun detachView() {
        super.detachView()
        model.removeListeners()
    }

    override fun updateChatItemColor(chatItem: IChatItem, key: String) {
        model.updateChatItemColor(chatItem, key)
    }

}
