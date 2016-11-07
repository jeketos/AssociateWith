package com.jeketos.associatewith.guesser;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.guesser.chat.IChatItem;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserMVP {

    public interface GuesserView{

        void init();

        void draw(float previousX, float previousY, Point point);

        void clearBoard();

        void clearChat();

        void addChatItem(IChatItem item);

        void showWinnerDialog(String name, String word);
    }

    public interface GuesserPresenter{

        void dataReceived(DataSnapshot point);

        void sendMessage(String message);

        void chatDataReceived(DataSnapshot dataSnapshot);

        void selectedWordReceived(DataSnapshot dataSnapshot);

        void winnerDataReceived(DataSnapshot dataSnapshot);
    }

    public interface GuesserModel{

        void sendMessage(IChatItem item);

        void setWinner(IChatItem item);
    }

}
