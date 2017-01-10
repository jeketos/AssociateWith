package com.jeketos.associatewith.ui.guesser

import android.text.TextUtils
import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.base.BaseMvpPresenter
import com.jeketos.associatewith.ui.chat.ChatItem
import com.jeketos.associatewith.util.ChatUtils
import java.util.*
import javax.inject.Inject

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class GuesserPresenterImpl @Inject constructor() : BaseMvpPresenter<GuesserMVP.GuesserView>(), GuesserMVP.GuesserPresenter {


     @Inject lateinit var model : GuesserMVP.GuesserModel
     var previousPointCount : Int = 0
     var previousChatCount : Int = 0
     var selectedWord : String? = null
     var isWinner : Boolean = false

    override fun init() {
        previousPointCount = 0
    }

    override fun moveDataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        if(childrenCount == 0){
            view?.clearBoard()
        } else {
            for (i in previousPointCount until childrenCount){
                if(dataSnapshot.child(i.toString()).value != null) {
                    val hashMap = dataSnapshot.child(i.toString()).value as HashMap<*, *>
                    val motionEvent = hashMap["motionEvent"] as Long
                    val x = hashMap["x"].toString().toFloat()
                    val y = hashMap["y"].toString().toFloat()
                    val color = hashMap["color"].toString().toInt()
                    val strokeWidth = hashMap["strokeWidth"].toString().toFloat()
                    view?.draw(Point(x, y, motionEvent.toInt(), color, strokeWidth))
                }
            }
        }
        previousPointCount = childrenCount
    }

    override fun chatDataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        if(childrenCount == 0){
            view?.clearChat()
        } else {
//            val list = Array(childrenCount, { i -> i })
//            val chatItems : ArrayList<IChatItem> = ArrayList()
//            list.forEach {
//                chatItems.add(ChatUtils.getChatItem(dataSnapshot, it))
//            }
            view?.updateChatItems(ChatUtils.getChatItems(dataSnapshot))
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
                view?.showWinnerDialog(name, selectedWord!!, isWinner)
            }
        }
    }

    override fun sendMessage(message: String) {
        val item = ChatItem()
        item.setName(android.os.Build.MODEL)
        item.setMessage(message)
        model.sendMessage(item)
        if(message.toLowerCase() == (selectedWord)){
            isWinner = true
            model.setWinner(item)
        }
    }

    override fun attachView(view: GuesserMVP.GuesserView) {
        super.attachView(view)
        model.init()
        model.addEventListeners()
        model.addChatListener { dataSnapshot: DataSnapshot -> chatDataReceived(dataSnapshot) }
        model.addMoveListener { dataSnapshot: DataSnapshot -> moveDataReceived(dataSnapshot) }
        model.addWinnerListener { dataSnapshot: DataSnapshot ->  winnerDataReceived(dataSnapshot)}
        model.addSelectedWordListener { dataSnapshot: DataSnapshot ->  selectedWordReceived(dataSnapshot)}
    }

    override fun detachView() {
        super.detachView()
        model.removeListeners()
    }
}
