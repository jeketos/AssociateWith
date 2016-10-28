package com.jeketos.associatewith.drawer;

import com.jeketos.associatewith.Point;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawMVP {

    public interface DrawView{

        void init();

    }

    public interface DrawPresenter{

        void sendPoint(Point point);

    }

    public interface DrawModel{

        int getMovesCount();

        void sendPoint(int movesCount, Point point);

        void clearData();
    }

}
