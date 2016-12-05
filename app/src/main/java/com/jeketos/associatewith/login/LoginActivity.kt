package com.jeketos.associatewith.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jeketos.associatewith.R
import com.jeketos.associatewith.drawer.DrawerActivity
import com.jeketos.associatewith.guesser.GuesserActivity
import kotlinx.android.synthetic.main.activity_login.*

/**
 * Created by jeketos on 05.12.2016.
 *
 */
class LoginActivity() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonDrawer.setOnClickListener {
            startDrawerActivity()
        }
        buttonGuesser.setOnClickListener {
            startGuesserActivity()
        }
    }

    private fun startDrawerActivity() {
        val intent = Intent(this, DrawerActivity::class.java)
        startActivity(intent)
    }

    private fun startGuesserActivity() {
        val intent = Intent(this, GuesserActivity::class.java)
        startActivity(intent)
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