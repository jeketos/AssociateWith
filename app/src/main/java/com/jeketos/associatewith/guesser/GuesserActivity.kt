package com.jeketos.associatewith.guesser

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MotionEvent
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.di.Injector
import com.jeketos.associatewith.guesser.chat.ChatAdapter
import com.jeketos.associatewith.guesser.chat.IChatItem
import com.jeketos.associatewith.util.DialogUtils
import kotlinx.android.synthetic.main.activity_guesser.*

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class GuesserActivity() : AppCompatActivity(), GuesserMVP.GuesserView {

    lateinit var presenter : GuesserMVP.GuesserPresenter
    lateinit var canvas : Canvas
    lateinit var paint : Paint
    lateinit var  chatAdapter : ChatAdapter

    fun onSendClick(){
         val message = editText.text.toString()
        if(!TextUtils.isEmpty(message)){
            presenter.sendMessage(message)
            editText.text = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guesser)
        presenter = Injector.provideGuesserPresenter(this)
    }

    override fun init() {
        val bitmap = Bitmap.createBitmap(resources.displayMetrics.widthPixels,
                resources.displayMetrics.heightPixels,
                Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        paint = Paint()
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        paint.isDither = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 12f
        send.setOnClickListener {onSendClick()}
        imageView.setImageBitmap(bitmap)
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter()
        chatRecyclerView.adapter = chatAdapter
    }

    override fun draw(previousX: Float, previousY: Float, point: Point) {
        when(point.motionEvent){
            MotionEvent.ACTION_DOWN -> drawPoint(point)
            else -> drawLine(previousX, previousY, point)
        }
    }

    override fun clearBoard() {
        init()
    }

    override fun clearChat() {
        chatAdapter.updateItems(null)
    }

    override fun addChatItem(item: IChatItem) {
        chatAdapter.updateItems(item)
    }

    override fun showWinnerDialog(name: String, word: String) {
        val builder = DialogUtils.createWinnerDialog(this, name, word)
        builder.create().show()
    }

    fun drawPoint(point: Point) {
        canvas.drawPoint(point.x, point.y, paint)
        imageView.invalidate()
    }

    fun drawLine(previousX: Float,previousY: Float,point: Point) {
        canvas.drawLine(previousX, previousY, point.x, point.y, paint)
        imageView.invalidate()
    }
}
