package com.jeketos.associatewith.drawer

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.base.BaseActivity
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.chat.ExtChatAdapter
import com.jeketos.associatewith.chat.IChatItem
import com.jeketos.associatewith.guesser.GuesserActivity
import com.jeketos.associatewith.listener.TouchWatcher
import com.jeketos.associatewith.util.DialogUtils
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.drawer_picker.*
import javax.inject.Inject

class DrawerActivity() : BaseActivity<DrawerMVP.DrawerView>(), DrawerMVP.DrawerView, ColorChooserDialog.ColorCallback {


    @Inject lateinit var presenter : DrawerMVP.DrawerPresenter
    lateinit var extChatAdapter: ExtChatAdapter

    val touchWatcher = object : TouchWatcher {

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
        setContentView(R.layout.drawer_layout)
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
        imageView.setMoveWatcher(touchWatcher)
        val layoutManager = LinearLayoutManager(this)
        chatRecyclerView.layoutManager = layoutManager
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        extChatAdapter = ExtChatAdapter(this)
        chatRecyclerView.adapter = extChatAdapter
        drawerImage.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.END)
        }
        clearButton.setOnClickListener {
            imageView.clear()
            presenter.clearDraw()
            drawerLayout.closeDrawer(GravityCompat.END)
        }
        chooseColorButton.setOnClickListener {
            ColorChooserDialog.Builder(this, R.string.change_color)
                    .titleSub(R.string.color_shades)  // title of dialog when viewing shades of a color
                    .accentMode(false)  // when true, will display accent palette instead of primary palette
                    .doneButton(R.string.md_done_label)  // changes label of the done button
                    .cancelButton(R.string.md_cancel_label)  // changes label of the cancel button
                    .backButton(R.string.md_back_label)  // changes label of the back button
                    .dynamicButtonColor(true)  // defaults to true, false will disable changing action buttons' color to currently selected color
                .show()
            drawerLayout.closeDrawer(GravityCompat.END)
        }
        strokeRecyclerView.layoutManager = LinearLayoutManager(this)
        strokeRecyclerView. adapter = StrokeWidthAdapter(this)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.peekHeight = resources.getDimension(R.dimen.bottom_sheet_peek).toInt()
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun onColorSelection(dialog: ColorChooserDialog, selectedColor: Int) {
        imageView.setDrawColor(selectedColor)
    }

    override fun addChatItem(chatItem: IChatItem?) {
        extChatAdapter.updateItems(chatItem)
    }

    override fun clearChat() {
        extChatAdapter.updateItems(null)
    }

    override fun setStrokeWidth(strokeWidth: Float) {
        imageView.setStrokeWidth(strokeWidth)
        drawerLayout.closeDrawer(GravityCompat.END)
    }

    override fun updateChatItemColor(chatItem: IChatItem, position: Int) {
        presenter.updateChatItemColor(chatItem, position)
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
        builder.setPositiveButton(android.R.string.ok, {d,i ->
            finish()
            startActivity(Intent(this, GuesserActivity::class.java))
        })
        builder.create()
        if(!this.isFinishing)
            builder.show()
    }
}
