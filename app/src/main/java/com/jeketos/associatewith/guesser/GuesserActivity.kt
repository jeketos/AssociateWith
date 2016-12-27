package com.jeketos.associatewith.guesser

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.base.BaseActivity
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.drawer.DrawerActivity
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

    override fun draw(point: Point) {
        imageView.setDrawColor(point.color)
        imageView.setStrokeWidth(point.strokeWidth)
        val motionEvent = MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), point.motionEvent, point.x, point.y, 0)
        imageView.onTouchEvent(motionEvent)
    }

    override fun clearBoard() {
        init()
        imageView.clear()
    }

    override fun setDrawColor(color: Int) {
        imageView.setDrawColor(color)
    }

    override fun clearChat() {
        chatAdapter.updateItems(null)
    }

    override fun addChatItem(item: IChatItem) {
        chatAdapter.updateItems(item)
    }

    override fun showWinnerDialog(name: String, word: String, isWinner: Boolean) {
        val builder = DialogUtils.createWinnerDialog(this, name, word)
        builder.setPositiveButton(android.R.string.ok, {d,i ->
            hideKeyboard()
            finish()
            if(isWinner) {
                startActivity(Intent(this, DrawerActivity::class.java))
            } else{
                startActivity(Intent(this, GuesserActivity::class.java))
            }
        })
        builder.create().show()
    }

    fun hideKeyboard(){
        if (getCurrentFocus() != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(getCurrentFocus()!!.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}
