package com.jeketos.associatewith.drawer;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jeketos.associatewith.Point;

import java.util.HashMap;
import java.util.Map;

import static com.jeketos.associatewith.guesser.GuesserModelImpl.WINNER;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class DrawerModelImpl implements DrawerMVP.DrawerModel {

    private static final String MOVE = "move";
    private static final String CHAT = "chat";
    public static final String WORDS = "words";
    public static final String SELECTED_WORD = "selected_word";
    DrawerMVP.DrawerPresenter presenter;
    private DatabaseReference referenceMove;
    private DatabaseReference referenceChat;
    private DatabaseReference referenceWords;
    private DatabaseReference referenceSelectedWord;
    private DatabaseReference referenceWinner;

    public DrawerModelImpl(DrawerMVP.DrawerPresenter presenter) {
        this.presenter = presenter;
        referenceSelectedWord = FirebaseDatabase.getInstance().getReference(SELECTED_WORD);
        referenceMove = FirebaseDatabase.getInstance().getReference(MOVE);
        referenceChat = FirebaseDatabase.getInstance().getReference(CHAT);
        referenceChat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.chatDataReceived(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        referenceWords = FirebaseDatabase.getInstance().getReference(WORDS);
        referenceWords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.wordsDataReceived(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        referenceWinner = FirebaseDatabase.getInstance().getReference(WINNER);
        referenceWinner.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.winnerDataReceived(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    //FIXME
    @Override
    public int getMovesCount() {
        return 0;
    }

    @Override
    public void sendPoint(int movesCount, Point point) {
        Map<String, Object> map = new HashMap<>();
        map.put(Integer.toString(movesCount), point);
        referenceMove.updateChildren(map);
    }

    @Override
    public void clearData() {
        referenceMove.removeValue();
        referenceChat.removeValue();
        presenter.clearChat();
    }

    @Override
    public void saveSelectedWord(CharSequence word) {
        referenceSelectedWord.setValue(word);
    }
}
