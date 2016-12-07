package com.jeketos.associatewith.drawer

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.di.Injector
import com.jeketos.associatewith.guesser.chat.ChatAdapter
import com.jeketos.associatewith.guesser.chat.IChatItem
import com.jeketos.associatewith.listener.TouchListener
import com.jeketos.associatewith.util.DialogUtils
import kotlinx.android.synthetic.main.activity_drawer.*

class DrawerActivity() : AppCompatActivity(), DrawerMVP.DrawerView {

//    private static final String TAG = "DrawerActivity";
    lateinit var canvas : Canvas
    lateinit var paint : Paint
    lateinit var presenter : DrawerMVP.DrawerPresenter
    lateinit var onTouchListener : TouchListener
    lateinit var chatAdapter : ChatAdapter

    val moveListener = object : TouchListener.MoveListener {

        override fun actionDown(point : Point) {
            drawPoint(point)
        }

        override fun actionMove(previousX: Float, previousY: Float, point: Point) {
            drawLine(previousX, previousY, point)
        }

        override fun actionUp(previousX: Float, previousY: Float, point: Point) {
            drawLine(previousX, previousY, point)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawer)
        onTouchListener = TouchListener(moveListener)
        presenter = Injector.provideDrawPresenter(this)
    }

    override fun init() {
        val displayMetrics = resources.displayMetrics
        val bitmap = Bitmap.createBitmap(displayMetrics.widthPixels,displayMetrics.heightPixels, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)
        paint = Paint()
        paint.color = Color.BLACK
        paint.isAntiAlias = true
        paint.isDither = true
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 12f
        imageView.setImageBitmap(bitmap)
        imageView.setOnTouchListener(onTouchListener)
        val layoutManager = LinearLayoutManager(this)
        chatRecyclerView.layoutManager = layoutManager
        chatAdapter = ChatAdapter()
        chatRecyclerView.adapter = chatAdapter
    }

    override fun addChatItem(chatItem: IChatItem) {
        chatAdapter.updateItems(chatItem)
    }

    override fun clearChat() {
        chatAdapter.updateItems(null)
    }

    override fun showChooseWordDialog(words: Array<CharSequence>) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.choose_word)
        builder.setCancelable(false)
        builder.setItems(words, { dialogInterface, i ->
            presenter.saveWord(words[i])
            dialogInterface.dismiss()
        })
        builder.create().show()
    }

    override fun showWinnerDialog(name: String) {
        val builder = DialogUtils.createWinnerDialog(this,name,null)
        builder.create().show()
    }

    fun drawPoint(point: Point){
        canvas.drawPoint(point.x, point.y, paint)
        imageView.invalidate()
        presenter.sendPoint(point)
    }

    fun drawLine(previousX: Float, previousY: Float, point: Point){
        canvas.drawLine(previousX, previousY, point.x, point.y, paint)
        imageView.invalidate()
        presenter.sendPoint(point)
    }
}
