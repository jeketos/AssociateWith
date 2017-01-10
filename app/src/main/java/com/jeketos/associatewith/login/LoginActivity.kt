package com.jeketos.associatewith.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jeketos.associatewith.R
import com.jeketos.associatewith.customViews.RoomDialog
import com.jeketos.associatewith.listener.RoomListener
import com.jeketos.associatewith.ui.drawer.DrawerActivity
import com.jeketos.associatewith.ui.guesser.GuesserActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by jeketos on 05.12.2016.
 *
 */
class LoginActivity : AppCompatActivity(), RoomListener {

    lateinit var roomDialog : RoomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonDrawer.setOnClickListener {
            startDrawerActivity()
        }
        buttonGuesser.setOnClickListener {
            startGuesserActivity()
        }

        buttonCreateRoom.setOnClickListener {
            showCreateRoomDialog()
        }

        buttonJoinRoom.setOnClickListener {
            showJoinRoomDialog()
        }
    }

    private fun showCreateRoomDialog() {
        roomDialog = RoomDialog.newInstance(Type.CREATE_ROOM)
        roomDialog.listener = this
        roomDialog.show(supportFragmentManager,"room_dialog")

    }

    private fun showJoinRoomDialog() {
        roomDialog = RoomDialog.newInstance(Type.JOIN_ROOM)
        roomDialog.listener = this
        roomDialog.show(supportFragmentManager,"room_dialog")
    }

    private fun startDrawerActivity() {
        val intent = Intent(this, DrawerActivity::class.java)
        startActivity(intent)
    }

    private fun startGuesserActivity() {
        val intent = Intent(this, GuesserActivity::class.java)
        startActivity(intent)
    }

    override fun roomCreated(roomName: String, roomPassword: String) {
        val reference = FirebaseDatabase.getInstance().reference
        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.child(roomName).exists()){
                    Toast.makeText(applicationContext , getString(R.string.error_create_room_message), Toast.LENGTH_LONG).show()
                } else {
                    val referenceRoom = FirebaseDatabase.getInstance().getReference(roomName)
                    referenceRoom.child("password").setValue(roomPassword)
                    roomDialog.dismiss()
                    startDrawerActivity()
                }
            }
        })

    }

    override fun roomJoined(roomName: String, roomPassword: String) {
        val referenceRoom = FirebaseDatabase.getInstance().getReference(roomName)
        referenceRoom.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {}

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val password = dataSnapshot.child("password").value as String?
                if(password == roomPassword){
                    roomDialog.dismiss()
                    startGuesserActivity()
                } else{
                    Toast.makeText(applicationContext ,getString(R.string.error_join_room_message), Toast.LENGTH_LONG).show()
                }
                referenceRoom.removeEventListener(this)
            }

        })
    }
//    private fun addWords() {
//        val words = FirebaseDatabase.getInstance().getReference("words")
//        words.removeValue()
//        val file = File(Environment.getExternalStorageDirectory() + "/word_rus.txt")
//        val text = ArrayList()
//
//        try {
//            val br = BufferedReader(FileReader(file))
//            var line: String
//
//            while ((line = br.readLine()) != null) {
//                text.add(line)
//            }
//            br.close()
//
//            val map = HashMap()
//            for (i in 0..text.size() - 1) {
//                map.put(Integer.toString(i), text.get(i))
//            }
//            words.updateChildren(map)
//
//        } catch (e: IOException) {
//        }
//
//    }

}