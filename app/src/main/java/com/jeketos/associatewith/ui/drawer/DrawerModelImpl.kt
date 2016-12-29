package com.jeketos.associatewith.ui.drawer

import com.google.firebase.database.*
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.ui.chat.IChatItem
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
    lateinit var chatListener: (DataSnapshot) -> Unit
    lateinit var wordsListener: (DataSnapshot) -> Unit
    lateinit var winnerListener: (DataSnapshot) -> Unit
    val chatEventListener = object : ValueEventListener{
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            chatListener(dataSnapshot!!)
        }
    }
    val wordsEventListener = object : ValueEventListener{
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            wordsListener(dataSnapshot!!)
        }
    }
    val winnerEventListener = object : ValueEventListener{
        override fun onCancelled(p0: DatabaseError?) {}

        override fun onDataChange(dataSnapshot: DataSnapshot?) {
            winnerListener(dataSnapshot!!)
        }
    }

    init {
        referenceSelectedWord = FirebaseDatabase.getInstance().getReference(SELECTED_WORD)
        referenceMove = FirebaseDatabase.getInstance().getReference(MOVE)
        referenceChat = FirebaseDatabase.getInstance().getReference(CHAT)
        referenceWords = FirebaseDatabase.getInstance().getReference(WORDS)
        referenceWinner = FirebaseDatabase.getInstance().getReference("winner")
    }

    override fun getMovesCount(): Int{
        return 0
    }

    override fun sendPoint(movesCount: Int, point: Point) {
        val map = HashMap<String,Any>()
        map.put(movesCount.toString(),point)
        referenceMove.updateChildren(map)
    }

    override fun updateChatItemColor(chatItem: IChatItem, position: Int) {
        referenceChat.updateChildren(mapOf(Pair(position.toString(), chatItem)))
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

    override fun addChatListener(chatListener: (DataSnapshot) -> Unit) {
        this.chatListener = chatListener
    }

    override fun addWordsListener(wordsListener: (DataSnapshot) -> Unit) {
        this.wordsListener = wordsListener
    }
    override fun addWinnerListener(winnerListener: (DataSnapshot) -> Unit) {
        this.winnerListener = winnerListener
    }

    override fun addEventListeners() {
        referenceChat.addValueEventListener(chatEventListener)
        referenceWords.addValueEventListener(wordsEventListener)
        referenceWinner.addValueEventListener(winnerEventListener)
    }

    override fun removeListeners() {
        referenceChat.removeEventListener(chatEventListener)
        referenceWords.removeEventListener(wordsEventListener)
        referenceWinner.removeEventListener(winnerEventListener)
    }

    override fun clearDraw() {
        referenceMove.removeValue()
    }
}
