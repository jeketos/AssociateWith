package com.jeketos.associatewith.util;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;

import com.jeketos.associatewith.R;

/**
 * Created by eugene.kotsogub on 11/7/16.
 *
 */

public class DialogUtils {

    public static AlertDialog.Builder createWinnerDialog(Context context, String name, String word){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.winner);
        String winner = context.getString(R.string.winner_is) + name;
        String sourceWord = "";
        if(!TextUtils.isEmpty(word)) {
            sourceWord = "\n" + context.getString(R.string.source_word) + word;
        }
        builder.setMessage(winner + sourceWord);
        builder.setPositiveButton(android.R.string.ok, (dialogInterface, i) -> dialogInterface.dismiss());
        return  builder;
    }

}
