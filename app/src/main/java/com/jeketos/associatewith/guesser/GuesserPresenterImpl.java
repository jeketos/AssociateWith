package com.jeketos.associatewith.guesser;

import android.text.TextUtils;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.di.Injector;
import com.jeketos.associatewith.guesser.chat.ChatItem;
import com.jeketos.associatewith.guesser.chat.IChatItem;
import com.jeketos.associatewith.util.ChatUtils;

import java.util.HashMap;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserPresenterImpl implements GuesserMVP.GuesserPresenter {

    private GuesserMVP.GuesserView view;
    private GuesserMVP.GuesserModel model;
    private float previousX, previousY;
    private int previousPointCount;
    private int previousChatCount;
    String selectedWord;

    public GuesserPresenterImpl(GuesserMVP.GuesserView view) {
        this.view = view;
        previousX = 0;
        previousY = 0;
        previousPointCount = 0;
        model = Injector.provideGuesserModel(this);
        view.init();
    }

    @Override
    public void dataReceived(DataSnapshot dataSnapshot) {
        int childrenCount = ((Number) dataSnapshot.getChildrenCount()).intValue();
        if(dataSnapshot.getChildrenCount() == 0){
            view.clearBoard();
        } else {
            for (int i = previousPointCount; i < childrenCount; i++) {
                int motionEvent = ((Number) ((HashMap) dataSnapshot.child(Integer.toString(i)).getValue()).get("motionEvent")).intValue();
                float x = getFloatValue(((HashMap) dataSnapshot.child(Integer.toString(i)).getValue()).get("x"));
                float y = getFloatValue(((HashMap) dataSnapshot.child(Integer.toString(i)).getValue()).get("y"));
                Point point = new Point(x, y, motionEvent);
                view.draw(previousX, previousY, point);
                previousX = x;
                previousY = y;
            }
        }
        previousPointCount = childrenCount;

    }

    @Override
    public void chatDataReceived(DataSnapshot dataSnapshot) {
        int childrenCount = ((Number) dataSnapshot.getChildrenCount()).intValue();
        if(dataSnapshot.getChildrenCount() == 0){
            view.clearChat();
        } else {
            for (int i = previousChatCount; i < childrenCount; i++) {
                view.addChatItem(ChatUtils.getChatItem(dataSnapshot,i));
            }
        }
        previousChatCount = childrenCount;
    }

    @Override
    public void selectedWordReceived(DataSnapshot dataSnapshot) {
        selectedWord = (String) dataSnapshot.getValue();
    }

    @Override
    public void winnerDataReceived(DataSnapshot dataSnapshot) {
        String name  = (String) dataSnapshot.getValue();
        String word = selectedWord;
        if(!TextUtils.isEmpty(name)) {
            view.showWinnerDialog(name, word);
        }
    }

    @Override
    public void sendMessage(String message) {
        IChatItem item = new ChatItem();
        item.setName(android.os.Build.MODEL);
        item.setMessage(message);
        model.sendMessage(item);
        if(message.toLowerCase().equals(selectedWord)){
            model.setWinner(item);
            view.showWinnerDialog(item.getName(), message);
        }
    }

    private float getFloatValue(Object value){
        return ((Number) value).floatValue();
    }

}
