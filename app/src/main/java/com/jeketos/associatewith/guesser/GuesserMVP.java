package com.jeketos.associatewith.guesser;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.Point;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserMVP {

    public interface GuesserView{

        void init();

        void draw(float previousX, float previousY, Point point);

        void clearBoard();
    }

    public interface GuesserPresenter{

        void dataReceived(DataSnapshot point);
    }

    public interface GuesserModel{

    }

}
