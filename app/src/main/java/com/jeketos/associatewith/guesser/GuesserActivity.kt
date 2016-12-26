package com.jeketos.associatewith.guesser

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MotionEvent
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.base.BaseActivity
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.guesser.chat.ChatAdapter
import com.jeketos.associatewith.guesser.chat.IChatItem
import com.jeketos.associatewith.util.DialogUtils
import kotlinx.android.synthetic.main.activity_guesser.*
import javax.inject.Inject

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class GuesserActivity() : BaseActivity<GuesserMVP.GuesserView>(), GuesserMVP.GuesserView {

    @Inject lateinit var presenter : GuesserMVP.GuesserPresenter
    lateinit var  chatAdapter : ChatAdapter

    fun onSendClick(){
         val message = editText.text.toString()
        if(!TextUtils.isEmpty(message)){
            presenter.sendMessage(message)
            editText.text = null
        }
    }

    override fun getPresenter(): BasePresenter<GuesserMVP.GuesserView> {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_guesser)
        init()
    }

    override fun onStart() {
        super.onStart()
        presenter.init()
    }

    override fun init() {
        send.setOnClickListener {onSendClick()}
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter()
        chatRecyclerView.adapter = chatAdapter
    }

    override fun draw(previousX: Float, previousY: Float, point: Point) {
        val motionEvent = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), point.motionEvent, point.x, point.y, 0)
        imageView.onTouchEvent(motionEvent)
//        when(point.motionEvent){
//            MotionEvent.ACTION_DOWN -> drawPoint(point)
//            else -> drawLine(previousX, previousY, point)
//        }
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
}
