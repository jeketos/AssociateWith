package com.jeketos.associatewith.drawer

import com.google.firebase.database.*
import com.jeketos.associatewith.Point
import java.util.*
import javax.inject.Inject

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */
 class DrawerModelImpl @Inject constructor() : DrawerMVP.DrawerModel {

    val  MOVE = "move"
    val  CHAT = "chat"
    val WORDS = "words"
    val SELECTED_WORD = "selected_word"
    val referenceMove : DatabaseReference
    val referenceChat : DatabaseReference
    val referenceWords : DatabaseReference
    val referenceSelectedWord : DatabaseReference
    val referenceWinner : DatabaseReference
    lateinit var firebaseListener: (DataSnapshot) -> Unit
    @Inject lateinit var presenter : DrawerMVP.DrawerPresenter

    init {
        referenceSelectedWord = FirebaseDatabase.getInstance().getReference(SELECTED_WORD)
        referenceMove = FirebaseDatabase.getInstance().getReference(MOVE)
        referenceChat = FirebaseDatabase.getInstance().getReference(CHAT)
        referenceWords = FirebaseDatabase.getInstance().getReference(WORDS)
        referenceWinner = FirebaseDatabase.getInstance().getReference("winner")
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
    }

    override fun saveSelectedWord(word: CharSequence) {
        referenceSelectedWord.setValue(word)
    }

    override fun setListener(firebaseListener: (DataSnapshot) -> Unit) {
        this.firebaseListener = firebaseListener
    }
}
