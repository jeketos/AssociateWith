package com.jeketos.associatewith.guesser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jeketos.associatewith.guesser.chat.IChatItem;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by eugene.kotsogub on 10/28/16.
 *
 */

public class GuesserModelImpl implements GuesserMVP.GuesserModel {

    private static final String MOVE = "move";
    public static final String CHAT = "chat";
    private GuesserMVP.GuesserPresenter presenter;
    DatabaseReference referenceChat;
    private int chatCount;


    public GuesserModelImpl(GuesserMVP.GuesserPresenter presenter) {

        this.presenter = presenter;
        init();

    }

    private void init() {
        chatCount = 0;
        DatabaseReference referenceMove = FirebaseDatabase.getInstance().getReference(MOVE);
        ValueEventListener moveValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.dataReceived(dataSnapshot);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        referenceMove.addValueEventListener(moveValueEventListener);
        referenceChat = FirebaseDatabase.getInstance().getReference(CHAT);
        ValueEventListener chatValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                presenter.chatDataReceived(dataSnapshot);
                chatCount = ((Number) dataSnapshot.getChildrenCount()).intValue();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        referenceChat.addValueEventListener(chatValueEventListener);
    }

    @Override
    public void sendMessage(IChatItem item) {
        Map<String, Object> map = new HashMap<>();
        map.put(Integer.toString(chatCount), item);
        referenceChat.updateChildren(map);
        chatCount++;
    }
}
