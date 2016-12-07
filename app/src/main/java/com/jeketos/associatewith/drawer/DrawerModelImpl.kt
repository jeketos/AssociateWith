package com.jeketos.associatewith.drawer

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.guesser.GuesserModelImpl

import java.util.*

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */
 class DrawerModelImpl(val presenter: DrawerMVP.DrawerPresenter) : DrawerMVP.DrawerModel {

    val  MOVE = "move"
    val  CHAT = "chat"
    val WORDS = "words"
    val SELECTED_WORD = "selected_word"
    val referenceMove : DatabaseReference
    val referenceChat : DatabaseReference
    val referenceWords : DatabaseReference
    val referenceSelectedWord : DatabaseReference
    val referenceWinner : DatabaseReference

    init {
        referenceSelectedWord = FirebaseDatabase.getInstance().getReference(SELECTED_WORD)
        referenceMove = FirebaseDatabase.getInstance().getReference(MOVE)
        referenceChat = FirebaseDatabase.getInstance().getReference(CHAT)
        referenceWords = FirebaseDatabase.getInstance().getReference(WORDS)
        referenceWinner = FirebaseDatabase.getInstance().getReference(GuesserModelImpl.WINNER)
        referenceChat.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                presenter.chatDataReceived(dataSnapshot!!)
            }
        })
        referenceWords.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                presenter.wordsDataReceived(dataSnapshot!!)
            }
        })
        referenceWinner.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                presenter.winnerDataReceived(dataSnapshot!!)
            }
        })
    }

    override fun getMovesCount(): Int{
        return 0
    }

    override fun sendPoint(movesCount: Int, point: Point) {
        val map = HashMap<String,Any>()
        map.put(movesCount.toString(),point)
        referenceMove.updateChildren(map)
    }

    override fun clearData() {
        referenceMove.removeValue()
        referenceChat.removeValue()
        referenceSelectedWord.removeValue()
        referenceWinner.removeValue()
        presenter.clearChat()
    }

    override fun saveSelectedWord(word: CharSequence) {
        referenceSelectedWord.setValue(word)
    }
}
