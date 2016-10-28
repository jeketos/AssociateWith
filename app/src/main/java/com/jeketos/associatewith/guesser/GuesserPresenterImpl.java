package com.jeketos.associatewith.guesser;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.di.Injector;

import java.util.HashMap;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserPresenterImpl implements GuesserMVP.GuesserPresenter {

    private GuesserMVP.GuesserView view;
    private GuesserMVP.GuesserModel model;
    private float previousX, previousY;

    public GuesserPresenterImpl(GuesserMVP.GuesserView view) {
        this.view = view;
        previousX = 0;
        previousY = 0;
        model = Injector.provideGuesserModel(this);
        view.init();
    }

    @Override
    public void dataReceived(DataSnapshot dataSnapshot) {
        if(dataSnapshot.getChildrenCount() == 0){
            view.clearBoard();
        } else {
            for (int i = 0; i < dataSnapshot.getChildrenCount(); i++) {
                int motionEvent = ((Number) ((HashMap) dataSnapshot.child(Integer.toString(i)).getValue()).get("motionEvent")).intValue();
                float x = getFloatValue(((HashMap) dataSnapshot.child(Integer.toString(i)).getValue()).get("x"));
                float y = getFloatValue(((HashMap) dataSnapshot.child(Integer.toString(i)).getValue()).get("y"));
                Point point = new Point(x, y, motionEvent);
                view.draw(previousX, previousY, point);
                previousX = x;
                previousY = y;
            }
        }

    }

    private float getFloatValue(Object value){
        return ((Number) value).floatValue();
    }

}
