package com.jeketos.associatewith.drawer

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.base.BaseActivity
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.guesser.chat.ChatAdapter
import com.jeketos.associatewith.guesser.chat.IChatItem
import com.jeketos.associatewith.listener.TouchWatcher
import com.jeketos.associatewith.util.DialogUtils
import kotlinx.android.synthetic.main.activity_drawer.*
import javax.inject.Inject

class DrawerActivity() : BaseActivity<DrawerMVP.DrawerView>(), DrawerMVP.DrawerView {

    @Inject lateinit var presenter : DrawerMVP.DrawerPresenter
    lateinit var chatAdapter : ChatAdapter

    val touchWather = object : TouchWatcher {

        override fun actionTouch(point: Point) {
            presenter.sendPoint(point)
        }
    }

    override fun getPresenter(): BasePresenter<DrawerMVP.DrawerView> {
        return presenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        setContentView(R.layout.activity_drawer)
        showProgressDialog()
        init()
    }

    override fun onStart() {
        super.onStart()
        presenter.init()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun init() {
        imageView.setMoveWatcher(touchWather)
        val layoutManager = LinearLayoutManager(this)
        chatRecyclerView.layoutManager = layoutManager
        chatAdapter = ChatAdapter()
        chatRecyclerView.adapter = chatAdapter
    }

    override fun addChatItem(chatItem: IChatItem?) {
        chatAdapter.updateItems(chatItem)
    }

    override fun clearChat() {
        chatAdapter.updateItems(null)
    }

    override fun showChooseWordDialog(words: Array<CharSequence>) {
        hideProgressDialog()
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
        builder.create()
        if(!this.isFinishing)
            builder.show()
    }
}
