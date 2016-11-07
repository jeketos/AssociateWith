package com.jeketos.associatewith.drawer;

import com.google.firebase.database.DataSnapshot;
import com.jeketos.associatewith.Point;
import com.jeketos.associatewith.guesser.chat.IChatItem;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawerMVP {

    public interface DrawerView {

        void init();

        void addChatItem(IChatItem chatItem);

        void clearChat();

        void showChooseWordDialog(CharSequence[] words);

        void showWinnerDialog(String name);
    }

    public interface DrawerPresenter {

        void sendPoint(Point point);

        void chatDataReceived(DataSnapshot dataSnapshot);

        void clearChat();

        void wordsDataReceived(DataSnapshot dataSnapshot);

        void saveWord(CharSequence word);

        void winnerDataReceived(DataSnapshot dataSnapshot);
    }

    public interface DrawerModel {

        int getMovesCount();

        void sendPoint(int movesCount, Point point);

        void clearData();

        void saveSelectedWord(CharSequence word);
    }

}
