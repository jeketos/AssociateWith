package com.jeketos.associatewith.guesser;

import com.jeketos.associatewith.Point;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserMVP {

    public interface GuesserView{

        void init();

        void drawPoint(Point point);

        void drawLine(float previousX, float previousY, Point point);
    }

    public interface GuesserPresenter{

    }

    public interface GuesserModel{

    }

}
