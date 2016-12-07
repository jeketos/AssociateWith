package com.jeketos.associatewith.drawer

import com.google.firebase.database.DataSnapshot
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.di.Injector
import com.jeketos.associatewith.util.ChatUtils
import java.util.Random

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class DrawerPresenterImpl(val view: DrawerMVP.DrawerView) : DrawerMVP.DrawerPresenter {

    val model : DrawerMVP.DrawerModel
    var movesCount : Int
    var previousChatCount : Int

    init {
        previousChatCount = 0
        model = Injector.provideDrawModel(this)
        view.init()
        model.clearData()
        movesCount = model.getMovesCount()
    }

    override fun sendPoint(point: Point) {
        model.sendPoint(movesCount, point)
        movesCount++
    }

    override fun chatDataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        if(childrenCount != 0){
            for(i in previousChatCount until childrenCount){
                view.addChatItem(ChatUtils.getChatItem(dataSnapshot, i))
            }
        }
        previousChatCount = childrenCount
    }

    override fun clearChat() {
        view.clearChat()
    }

    override fun wordsDataReceived(dataSnapshot: DataSnapshot) {
        val childrenCount = dataSnapshot.childrenCount.toInt()
        val random = Random()
        if(childrenCount != 0){
            val words : Array<CharSequence> = kotlin.arrayOf("","","")
            for (i in 0 until 3){
                words[i] = dataSnapshot.child(random.nextInt(childrenCount).toString()).value as CharSequence
            }
            view.showChooseWordDialog(words)
        }
    }

    override fun saveWord(word: CharSequence) {
        model.saveSelectedWord(word)
    }

    override fun winnerDataReceived(dataSnapshot: DataSnapshot) {
        val name = dataSnapshot.value as String?
        if(name != null) view.showWinnerDialog(name)
    }
}
