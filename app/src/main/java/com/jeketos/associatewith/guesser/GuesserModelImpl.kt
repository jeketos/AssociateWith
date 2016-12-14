package com.jeketos.associatewith.guesser

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jeketos.associatewith.guesser.chat.IChatItem
import javax.inject.Inject

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

 class GuesserModelImpl @Inject constructor() : GuesserMVP.GuesserModel {

    val  MOVE = "move"
    val CHAT = "chat"
    val SELECTED_WORD = "selected_word"
    val WINNER = "winner"
    var chatCount : Int = 0
    var referenceChat : DatabaseReference
    var referenceSelectedWord : DatabaseReference
    var referenceWinner : DatabaseReference
    lateinit var chatListener: (DataSnapshot) -> Unit
    lateinit var moveListener: (DataSnapshot) -> Unit
    lateinit var selectedWordListener: (DataSnapshot) -> Unit
    lateinit var winnerListener: (DataSnapshot) -> Unit

    init {
        val referenceMove = FirebaseDatabase.getInstance().getReference(MOVE)
        referenceMove.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                moveListener(dataSnapshot)
            }
        })
        referenceChat = FirebaseDatabase.getInstance().getReference(CHAT)
        referenceChat.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                chatListener(dataSnapshot)
                chatCount = dataSnapshot.childrenCount.toInt()
            }

        })
        referenceSelectedWord = FirebaseDatabase.getInstance().getReference(SELECTED_WORD)
        referenceSelectedWord.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                selectedWordListener(dataSnapshot)
            }

        })
        referenceWinner = FirebaseDatabase.getInstance().getReference(WINNER)
        referenceWinner.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                winnerListener(dataSnapshot)
            }
        })
    }

    override fun sendMessage(item: IChatItem) {
        val map = hashMapOf(Pair(chatCount.toString(),item as Any))
        referenceChat.updateChildren(map)
    }

    override fun setWinner(item: IChatItem) {
        referenceWinner.setValue(item.getName())
    }

    override fun addChatListener(chatListener: (DataSnapshot) -> Unit) {
        this.chatListener = chatListener
    }

    override fun addMoveListener(moveListener: (DataSnapshot) -> Unit) {
        this.moveListener = moveListener
    }

    override fun addSelectedWordListener(selectedWordListener: (DataSnapshot) -> Unit) {
        this.selectedWordListener = selectedWordListener
    }

    override fun addWinnerListener(winnerListener: (DataSnapshot) -> Unit) {
        this.winnerListener = winnerListener
     }
}
