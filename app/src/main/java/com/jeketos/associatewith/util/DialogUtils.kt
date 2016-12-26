package com.jeketos.associatewith.util

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.jeketos.associatewith.R

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

object DialogUtils {

    fun createWinnerDialog(context : Context, name : String, word : String?) : AlertDialog.Builder{
        val builder = AlertDialog.Builder(context)
        builder.setTitle(R.string.winner)
        val winner = context.getString(R.string.winner_is) + name
        var sourceWord = ""
        if(!TextUtils.isEmpty(word)) {
            sourceWord = "\n" + context.getString(R.string.source_word) + word
        }
        builder.setMessage(winner + sourceWord)
        builder.setPositiveButton(android.R.string.ok, {dialogInterface, i -> dialogInterface.dismiss()})
        return  builder
    }

    fun createProgressDialog(context: Context): ProgressDialog {
        val dialog = ProgressDialog(context)
        val window = dialog.window
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        dialog.show()
        dialog.setContentView(R.layout.item_progress_dialog)
        return dialog
    }

}
