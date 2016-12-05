package com.jeketos.associatewith.drawer;

import android.text.TextUtils;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.di.Injector;
import com.jeketos.associatewith.util.ChatUtils;

import java.util.Random;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawerPresenterImpl implements DrawerMVP.DrawerPresenter {

    private DrawerMVP.DrawerView view;
    private DrawerMVP.DrawerModel model;
    private int movesCount;
    private int previousChatCount;


    public DrawerPresenterImpl(DrawerMVP.DrawerView view) {
        this.view = view;
        init();
    }

    private void init() {
        previousChatCount = 0;
        model = Injector.INSTANCE.provideDrawModel(this);
        view.init();
        model.clearData();
        movesCount = model.getMovesCount();
    }


    @Override
    public void sendPoint(Point point) {
        model.sendPoint(movesCount, point);
        movesCount++;
    }

    @Override
    public void chatDataReceived(DataSnapshot dataSnapshot) {
        int childrenCount = ((Number) dataSnapshot.getChildrenCount()).intValue();
        if(dataSnapshot.getChildrenCount() != 0){
            for (int i = previousChatCount; i < childrenCount; i++) {
                view.addChatItem(ChatUtils.getChatItem(dataSnapshot,i));
            }
        }
        previousChatCount = childrenCount;
    }

    @Override
    public void clearChat() {
        view.clearChat();
    }

    @Override
    public void wordsDataReceived(DataSnapshot dataSnapshot) {
        int childrenCount = ((Number) dataSnapshot.getChildrenCount()).intValue();
        Random random = new Random();
        if(dataSnapshot.getChildrenCount() != 0){
            CharSequence[] words = new CharSequence[3];
            for (int i = 0; i < 3; i++) {
                String word = (String) dataSnapshot.child(Integer.toString(random.nextInt(childrenCount))).getValue();
                words[i] = word;
            }
            view.showChooseWordDialog(words);
        }
    }

    @Override
    public void saveWord(CharSequence word) {
        model.saveSelectedWord(word);
    }

    @Override
    public void winnerDataReceived(DataSnapshot dataSnapshot) {
        String name = (String) dataSnapshot.getValue();
        if(!TextUtils.isEmpty(name)) {
            view.showWinnerDialog(name);
        }
    }
}
