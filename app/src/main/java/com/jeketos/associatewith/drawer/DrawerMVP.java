package com.jeketos.associatewith.drawer;

import com.jeketos.associatewith.Point;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawerMVP {

    public interface DrawerView {

        void init();

    }

    public interface DrawerPresenter {

        void sendPoint(Point point);

    }

    public interface DrawerModel {

        int getMovesCount();

        void sendPoint(int movesCount, Point point);

        void clearData();
    }

}
