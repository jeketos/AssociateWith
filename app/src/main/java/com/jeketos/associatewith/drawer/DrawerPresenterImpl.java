package com.jeketos.associatewith.drawer;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.di.Injector;
import com.jeketos.associatewith.util.ChatUtils;

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
        model = Injector.provideDrawModel(this);
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
}
