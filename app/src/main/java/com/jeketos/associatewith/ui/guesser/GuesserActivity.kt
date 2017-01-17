package com.jeketos.associatewith.ui.guesser

import android.content.Context
import android.content.Intent
import android.inputmethodservice.Keyboard
import android.inputmethodservice.KeyboardView
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.base.BaseActivity
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.ui.chat.ChatAdapter
import com.jeketos.associatewith.ui.chat.IChatItem
import com.jeketos.associatewith.ui.drawer.DrawerActivity
import com.jeketos.associatewith.util.DialogUtils
import kotlinx.android.synthetic.main.activity_guesser.*
import java.util.*
import javax.inject.Inject

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

class GuesserActivity : BaseActivity<GuesserMVP.GuesserView>(), GuesserMVP.GuesserView {

    @Inject lateinit var presenter : GuesserMVP.GuesserPresenter
    lateinit var chatAdapter: ChatAdapter
    var density = 0
    val handler = Handler()
    lateinit var keyboard : Keyboard

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
        density = resources.displayMetrics.densityDpi
        init()
    }

    override fun onStart() {
        super.onStart()
        presenter.init()
    }

    override fun onStop() {
        handler.removeCallbacks(removeLetterRunnable)
        super.onStop()
    }

    override fun init() {
        send.setOnClickListener {onSendClick()}
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatAdapter = ChatAdapter()
        chatRecyclerView.adapter = chatAdapter
        keyboard = Keyboard(this, R.xml.keyboard)
        // Attach the keyboard to the view
        keyboardView.keyboard = keyboard
        keyboardView.setOnKeyboardActionListener(onKeyboardActionListener)
        keyboardView.isPreviewEnabled = false
        editText.setOnTouchListener(exitSoftKeyBoard)
        editText.setOnFocusChangeListener { view, b ->
            if(b){
                keyboardView.visibility = View.VISIBLE
            }
        }
    }

    private val exitSoftKeyBoard = View.OnTouchListener { v, event ->
        if (v == editText) {
            editText.requestFocus()
            keyboardView.visibility = View.VISIBLE
        }
        true
    }

    private var removeLetterRunnable: Runnable? = null
    private val onKeyboardActionListener = object : KeyboardView.OnKeyboardActionListener {
        override fun onKey(primaryCode: Int, keyCodes: IntArray) {
            val length = editText.text.length
            if (primaryCode == 100) {
                onSendClick()
            } else if (primaryCode != -1) {
                editText.text.append(getKeyLabel(primaryCode))
                editText.setSelection(length + 1)
            }
        }

        override fun onPress(primaryCode: Int) {
            if (primaryCode == -1) {
                if (editText.text.isNotEmpty()) {
                    editText.text.delete(editText.text.length - 1, editText.text.length)
                    editText.setSelection(editText.text.length)
                    removeLetterRunnable = object : Runnable {
                        override fun run() {
                            if (editText.text.isNotEmpty()) {
                                editText.text.delete(editText.text.length - 1, editText.text.length)
                                editText.setSelection(editText.text.length)
                                handler.postDelayed(this, 100)
                            }
                        }
                    }
                    handler.postDelayed(removeLetterRunnable, 500)
                }
            }
        }

        override fun onRelease(primaryCode: Int) {
            if (primaryCode == -1) {
                handler.removeCallbacks(removeLetterRunnable)
            }
        }

        override fun onText(text: CharSequence) {
        }

        override fun swipeDown() {}

        override fun swipeLeft() {}

        override fun swipeRight() {}

        override fun swipeUp() {}
    }

    fun getKeyLabel(keycode: Int): String {
        val keys = keyboard.keys
        keys
                .filter { it.codes[0] == keycode }
                .forEach { return it.label.toString() }
        return ""

    }

    override fun draw(point: Point) {
        val motionEvent = MotionEvent.obtain(System.currentTimeMillis(),
                System.currentTimeMillis(),
                point.motionEvent,
                point.x*imageView.bitmapWidth,
                point.y*imageView.bitmapHeight,
                0)
        with(imageView){
            setDrawColor(point.color)
            setStrokeWidth(point.strokeWidth)
            onTouchEvent(motionEvent)
        }
    }

    override fun clearBoard() {
        imageView.clear()
    }

    override fun setDrawColor(color: Int) {
        imageView.setDrawColor(color)
    }

    override fun clearChat() {
        chatAdapter.updateItems(null)
    }

    override fun updateChatItems(items: ArrayList<IChatItem>) {
        chatAdapter.updateItems(items)
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
        val dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
    }

    fun hideKeyboard(){
        if (currentFocus != null) {
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    override fun onBackPressed() {
        if(keyboardView.visibility == View.VISIBLE){
            keyboardView.visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}
