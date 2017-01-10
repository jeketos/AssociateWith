package com.jeketos.associatewith.customViews

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.EditText
import com.jeketos.associatewith.App
import com.jeketos.associatewith.R
import com.jeketos.associatewith.listener.RoomListener
import com.jeketos.associatewith.storage.Storer

/**
 * Created by eugene.kotsogub on 1/10/17.
 *
 */
class RoomDialog : AppCompatDialogFragment() {

    lateinit var storer : Storer
    lateinit var listener : RoomListener
    lateinit var roomName : EditText
    lateinit var roomPassword : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        storer = (this.activity.applicationContext as App).component.getStorer()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val v = LayoutInflater.from(activity).inflate(R.layout.dialog_room, null, false)
        roomName = v.findViewById(R.id.roomName) as EditText
        roomPassword = v.findViewById(R.id.roomPassword) as EditText
        val dialog = AlertDialog.Builder(activity)
                .setTitle(getString(R.string.create_room))
                .setView(v)
                .setPositiveButton(android.R.string.ok, null)
                .setNegativeButton(android.R.string.cancel,{d,i -> })
                .create()
        return dialog
    }

    override fun onStart() {
        super.onStart()
        (dialog as AlertDialog).getButton(AlertDialog.BUTTON_POSITIVE)
                .setOnClickListener { onPositiveButtonClick() }
    }

    private fun onPositiveButtonClick() {
        val roomName = roomName.text.toString()
        val roomPassword = roomPassword.text.toString()
        if(TextUtils.isEmpty(roomName) || TextUtils.isEmpty(roomPassword)){
            val builder = AlertDialog.Builder(activity)
                    .setMessage(getString(R.string.please_fill_all_fields))
                    .setPositiveButton(android.R.string.ok, null)
            builder.create().show()
        } else {
            storer.saveRoomName(roomName)
            storer.saveRoomPassword(roomPassword)
            dialog.dismiss()
            listener.roomCreated(roomName, roomPassword)
        }
    }
}