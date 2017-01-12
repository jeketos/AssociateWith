package com.jeketos.associatewith.ui.drawer

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.view.GravityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.afollestad.materialdialogs.color.ColorChooserDialog
import com.jeketos.associatewith.Point
import com.jeketos.associatewith.R
import com.jeketos.associatewith.base.BaseActivity
import com.jeketos.associatewith.base.BasePresenter
import com.jeketos.associatewith.listener.TouchWatcher
import com.jeketos.associatewith.ui.chat.ExtChatAdapter
import com.jeketos.associatewith.ui.chat.IChatItem
import com.jeketos.associatewith.ui.guesser.GuesserActivity
import com.jeketos.associatewith.util.DialogUtils
import kotlinx.android.synthetic.main.activity_drawer.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.drawer_picker.*
import java.util.*
import javax.inject.Inject

class DrawerActivity : BaseActivity<DrawerMVP.DrawerView>(), DrawerMVP.DrawerView, ColorChooserDialog.ColorCallback {


    @Inject lateinit var presenter : DrawerMVP.DrawerPresenter
    lateinit var extChatAdapter: ExtChatAdapter
    var  selectedWord: String? = null
    private var  chooseWordsDialog: AlertDialog? = null
    private var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>? = null

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
        if(savedInstanceState == null) {
            showProgressDialog()
            init()
        } else {
            selectedWord = savedInstanceState.getString("selected_word")
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString("selected_word", selectedWord)
        super.onSaveInstanceState(outState)
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
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior!!.peekHeight = resources.getDimension(R.dimen.bottom_sheet_peek).toInt()
        bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        bottomSheetBehavior!!.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("DrawerActivity","slide offset" + slideOffset * 180)
                chatDrawerImage.rotation = slideOffset * 180
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) { }
        })
        chatDrawerImage.setOnClickListener {
           when (bottomSheetBehavior!!.state){
               BottomSheetBehavior.STATE_COLLAPSED -> {
                   bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
                   chatDrawerImage.rotation = 180f
               }
               BottomSheetBehavior.STATE_EXPANDED -> {
                   bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
                   chatDrawerImage.rotation = 0f
               }
           }
        }
    }

    override fun onColorSelection(dialog: ColorChooserDialog, selectedColor: Int) {
        imageView.setDrawColor(selectedColor)
    }

    override fun addChatItems(chatItem: ArrayList<IChatItem>) {
        extChatAdapter.updateItems(chatItem)
    }

    override fun clearChat() {
        extChatAdapter.updateItems(null)
    }

    override fun setStrokeWidth(strokeWidth: Float) {
        imageView.setStrokeWidth(strokeWidth)
        drawerLayout.closeDrawer(GravityCompat.END)
    }

    override fun updateChatItemColor(chatItem: IChatItem, key: String) {
        presenter.updateChatItemColor(chatItem, key)
    }

    override fun showChooseWordDialog(words: Array<CharSequence>) {
        if(TextUtils.isEmpty(selectedWord) && chooseWordsDialog == null) {
            hideProgressDialog()
            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.choose_word)
            builder.setCancelable(false)
            builder.setItems(words, { dialogInterface, i ->
                selectedWord = words[i].toString()
                selectedWordTextView.text = selectedWord
                presenter.saveWord(words[i])
                dialogInterface.dismiss()
            })
            chooseWordsDialog = builder.create()
            if(!chooseWordsDialog!!.isShowing) {
                chooseWordsDialog?.show()
            }
        }
    }

    override fun showWinnerDialog(name: String) {
        if(selectedWord != null) {
            val builder = DialogUtils.createWinnerDialog(this, name, null)
            builder.setPositiveButton(android.R.string.ok, { d, i ->
                finish()
                startActivity(Intent(this, GuesserActivity::class.java))
            })
            val dialog = builder.create()
            dialog.setCanceledOnTouchOutside(false)
            if (!this.isFinishing)
                dialog.show()
        }
    }

    override fun onBackPressed() {
        if(bottomSheetBehavior != null && bottomSheetBehavior!!.state == BottomSheetBehavior.STATE_EXPANDED){
            bottomSheetBehavior!!.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            super.onBackPressed()
        }
    }
}
