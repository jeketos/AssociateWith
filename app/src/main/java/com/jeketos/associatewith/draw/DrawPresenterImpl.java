package com.jeketos.associatewith.draw;

import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.di.Injector;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawPresenterImpl implements DrawMVP.DrawPresenter {

    private DrawMVP.DrawView view;
    private DrawMVP.DrawModel model;
    private int movesCount;


    public DrawPresenterImpl(DrawMVP.DrawView view) {
        this.view = view;
        init();
    }

    private void init() {
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
}
