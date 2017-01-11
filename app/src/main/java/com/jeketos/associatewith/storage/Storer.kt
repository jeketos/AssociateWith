package com.jeketos.associatewith.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import javax.inject.Inject

/**
 * Created by eugene.kotsogub on 1/10/17.
 *
 */
class Storer {

    private var prefs: SharedPreferences

    @Inject constructor(app : Application){
        prefs = app.getSharedPreferences("data", Context.MODE_PRIVATE)
    }


    fun saveRoomName(roomName: String?){
        prefs.edit()
                .putString("room_name", roomName).
                apply()
    }

    fun getRoomName() : String{
       return prefs.getString("room_name","")
    }

    fun saveRoomPassword(roomName: String?){
        prefs.edit()
                .putString("room_password", roomName).
                apply()
    }

    fun getRoomPassword() : String{
        return prefs.getString("room_password","")
    }

}