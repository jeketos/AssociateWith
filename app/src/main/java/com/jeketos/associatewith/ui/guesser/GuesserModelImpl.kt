package com.jeketos.associatewith.ui.guesser

import com.google.firebase.database.*
import com.jeketos.associatewith.ui.chat.IChatItem
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
    var referenceMove : DatabaseReference
    var referenceColor : DatabaseReference
    lateinit var chatListener: (DataSnapshot) -> Unit
    lateinit var moveListener: (DataSnapshot) -> Unit
    lateinit var selectedWordListener: (DataSnapshot) -> Unit
    lateinit var winnerListener: (DataSnapshot) -> Unit
    val moveEventListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            moveListener(dataSnapshot)
        }
    }
    val chatEventListener = object : ValueEventListener{
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            chatListener(dataSnapshot)
            chatCount = dataSnapshot.childrenCount.toInt()
        }

    }
    val selectedWordEventListener = object : ValueEventListener {
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            selectedWordListener(dataSnapshot)
        }

    }
    val winnerEventListener = object : ValueEventListener{
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onDataChange(dataSnapshot: DataSnapshot) {
            winnerListener(dataSnapshot)
        }
    }

    init {
        referenceMove = FirebaseDatabase.getInstance().getReference(MOVE)
        referenceChat = FirebaseDatabase.getInstance().getReference(CHAT)
        referenceSelectedWord = FirebaseDatabase.getInstance().getReference(SELECTED_WORD)
        referenceWinner = FirebaseDatabase.getInstance().getReference(WINNER)
        referenceColor = FirebaseDatabase.getInstance().getReference("color")
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

    override fun addEventListeners() {
        referenceMove.addValueEventListener(moveEventListener)
        referenceChat.addValueEventListener(chatEventListener)
        referenceSelectedWord.addValueEventListener(selectedWordEventListener)
        referenceWinner.addValueEventListener(winnerEventListener)
    }

    override fun removeListeners() {
        referenceWinner.removeEventListener(winnerEventListener)
        referenceChat.removeEventListener(chatEventListener)
        referenceSelectedWord.removeEventListener(selectedWordEventListener)
        referenceMove.removeEventListener(moveEventListener)
    }
}
