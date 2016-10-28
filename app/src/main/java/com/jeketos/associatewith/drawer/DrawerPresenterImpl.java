package com.jeketos.associatewith.drawer;

import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.di.Injector;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawerPresenterImpl implements DrawerMVP.DrawerPresenter {

    private DrawerMVP.DrawerView view;
    private DrawerMVP.DrawerModel model;
    private int movesCount;


    public DrawerPresenterImpl(DrawerMVP.DrawerView view) {
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
